package com.lezrak.scrabblebackend;

import com.lezrak.scrabblebackend.game.GameController;
import com.lezrak.scrabblebackend.game.GameDTO;
import com.lezrak.scrabblebackend.gameName.GameNameService;
import com.lezrak.scrabblebackend.player.PlayerController;
import com.lezrak.scrabblebackend.player.PlayerDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrabbleBackendApplicationTests {


    @Autowired
    PlayerController playerController;

    @Autowired
    GameController gameController;

    @MockBean
    GameNameService gameNameServiceMock;

    @Test
    public void contextLoads() {

    }


    // I am fully aware these aren't by any means proper tests.
    // Resources are limited at the moment and the priority is to deploy functional alpha version of app on the
    // web server to allow frontend developers integrating their applications with the API.
    // Due to those circumstances I decided to implement tests this way, which I see as the least time consuming while
    // at the same time giving giving reasons to assume the basic functions are working.

    @Test
    public void playerControllerIntegrationTest() {

        PlayerDTO playerDTO = new PlayerDTO("testPlayer@gmail.com", "Chad", null);
        playerDTO.setPassword("safeCombination");

        // check credentials and postPlayer
        Assert.assertFalse(playerController.checkCredentials(playerDTO));
        PlayerDTO persistedPlayer = playerController.postPlayer(playerDTO);
        Assert.assertTrue(playerController.checkCredentials(playerDTO));

        //getGames
        Assert.assertEquals(0, playerController.getGames(persistedPlayer.getId()).size());
    }


    @Test
    public void gameControllerIntegrationTest() {
        PlayerDTO persistedPlayer = playerController.postPlayer(
                new PlayerDTO("testPlayerJunior@gmail.com", "ChadJunior", null));
        when(gameNameServiceMock.generateName()).thenReturn("popuśćmy");

        //getLobby and CreateGame
        Assert.assertEquals(0, gameController.getLobby().size());
        GameDTO persistedGameDTO = gameController.createGame(persistedPlayer.getId());
        Assert.assertEquals("popuśćmy", persistedGameDTO.getName());
        Assert.assertEquals(-1, persistedGameDTO.getNextPlayer());
        Assert.assertEquals(1, persistedGameDTO.getPlayers().size());
        Assert.assertEquals(1, gameController.getLobby().size());

        //findByName
        Assert.assertEquals(persistedGameDTO, gameController.findByName(persistedGameDTO.getName()));


        //addPlayer
        PlayerDTO addedPlayer = playerController.postPlayer(
                new PlayerDTO("testPlayerSenior@gmail.com", "ChadSenior", null));
        Assert.assertEquals(1, gameController.findByName(persistedGameDTO.getName()).getPlayers().size());
        gameController.addPlayer(addedPlayer.getId(), persistedGameDTO.getName());
        Assert.assertEquals(2, gameController.findByName(persistedGameDTO.getName()).getPlayers().size());

        //removePlayer
        gameController.removePlayer(addedPlayer.getId(), persistedGameDTO.getName());
        Assert.assertEquals(1, gameController.findByName(persistedGameDTO.getName()).getPlayers().size());

        //startGame
        gameController.addPlayer(addedPlayer.getId(), persistedGameDTO.getName());
        Assert.assertEquals(1, gameController.getLobby().size());
        gameController.startGame(persistedGameDTO.getName());
        Assert.assertEquals(0, gameController.getLobby().size());

        //makeMove
        Assert.assertTrue(gameController.findByName(persistedGameDTO.getName()).getNextPlayer() >= 0);
        Assert.assertTrue(gameController.findByName(persistedGameDTO.getName()).getNextPlayer() <= 1);
        int previousNextPlayer = gameController.findByName(persistedGameDTO.getName()).getNextPlayer();
        gameController.makeMove(persistedGameDTO.getName(), new HashMap<>(), persistedPlayer.getId());
        Assert.assertEquals((previousNextPlayer + 1) % 2, gameController.findByName(persistedGameDTO.getName()).getNextPlayer());


    }

}

