package com.lezrak.scrabblebackend;

import com.lezrak.scrabblebackend.game.GameController;
import com.lezrak.scrabblebackend.game.GameDTO;
import com.lezrak.scrabblebackend.game.PlayerState;
import com.lezrak.scrabblebackend.gameName.GameNameService;
import com.lezrak.scrabblebackend.player.PlayerController;
import com.lezrak.scrabblebackend.player.PlayerDTO;
import com.lezrak.scrabblebackend.player.PlayerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrabbleBackendApplicationTests {


    @Autowired
    PlayerRepository playerRepository;

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
    public void developmentTest() {
        HashMap<String, Character> move = new HashMap<>();
        move.put("H1", 'T');
        move.put("H2", 'O');
        move.put("H3", 'J');
        move.put("H4", 'E');
        move.put("H5", 'S');
        move.put("H6", 'T');
        move.put("H7", 'S');
        move.put("H8", 'T');
        move.put("B5", 'R');
        move.put("B6", 'I');
        move.put("B7", 'N');
        move.put("B8", 'G');
        System.out.println(move.toString());
    }

    @Test
    public void playerControllerIntegrationTest() throws MessagingException {

        PlayerDTO playerDTO = new PlayerDTO("", "Chad", null);
        playerDTO.setPassword("safeCombination");

        // postPlayer
        PlayerDTO persistedPlayer = playerController.postPlayer(playerDTO);
        Assert.assertEquals(playerDTO.getNickname(), persistedPlayer.getNickname());
        Assert.assertEquals(playerDTO.getEmail(), persistedPlayer.getEmail());

        //getGames
        Assert.assertEquals(0, playerController.getGames(persistedPlayer.getId()).size());
    }

    @Test
    public void playerStateTest() {

        PlayerState playerState = new PlayerState();
        Assert.assertEquals(0,playerState.getCharacters().size());

        ArrayList<Character> addedChars = new ArrayList<>();
        addedChars.add('A');
        addedChars.add('B');
        addedChars.add('B');
        addedChars.add('B');
        addedChars.add('C');
        addedChars.add('D');
        addedChars.add('D');
        Assert.assertEquals(7,addedChars.size());

        playerState.addCharacters(addedChars);
        Assert.assertEquals(7,playerState.getCharacters().size());

        addedChars = new ArrayList<>();
        addedChars.add('D');
        Assert.assertEquals(1,addedChars.size());

        playerState.removeCharacters(addedChars);
        Assert.assertEquals(6,playerState.getCharacters().size());


        addedChars = new ArrayList<>();
        addedChars.add('B');
        addedChars.add('B');
        Assert.assertEquals(2,addedChars.size());
        playerState.removeCharacters(addedChars);
        Assert.assertEquals(4,playerState.getCharacters().size());
    }

    @Test
    public void gameControllerIntegrationTest() throws MessagingException {
        PlayerDTO persistedPlayer = playerController.postPlayer(
                new PlayerDTO("", "ChadJunior", null));
        when(gameNameServiceMock.generateName()).thenReturn("popuśćmy");

        //getLobby and CreateGame
        Assert.assertEquals(0, gameController.getLobby().size());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                playerRepository.findPlayerById(persistedPlayer.getId()), null, Collections.emptySet()));
        GameDTO persistedGameDTO = gameController.createGame(persistedPlayer.getId());
        Assert.assertEquals("popuśćmy", persistedGameDTO.getName());
        Assert.assertEquals(-1, persistedGameDTO.getNextPlayer());
        Assert.assertEquals(1, persistedGameDTO.getPlayers().size());
        Assert.assertEquals(1, gameController.getLobby().size());

        //findByName
        Assert.assertEquals(persistedGameDTO, gameController.findByName(persistedGameDTO.getName()));


        //addPlayer
        PlayerDTO addedPlayer = playerController.postPlayer(
                new PlayerDTO("", "ChadSenior", null));
        Assert.assertEquals(1, gameController.findByName(persistedGameDTO.getName()).getPlayers().size());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                playerRepository.findPlayerById(addedPlayer.getId()), null, Collections.emptySet()));
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
        Long nextPlayerId = addedPlayer.getId();
        if (previousNextPlayer == 0) {
            nextPlayerId = persistedPlayer.getId();
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                playerRepository.findPlayerById(nextPlayerId), null, Collections.emptySet()));
        HashMap<String, Character> move = new HashMap<>();
        move.put("H8", 'T');
        move.put("H9", 'O');
        gameController.makeMove(persistedGameDTO.getName(), move, nextPlayerId);

        Assert.assertEquals(3,
                gameController.findByName(persistedGameDTO.getName())
                        .getPlayers()
                        .stream()
                        .mapToInt(GameDTO.PlayerStateDTO::getLastMovePoints)
                        .sum());
        Assert.assertEquals((previousNextPlayer + 1) % 2, gameController.findByName(persistedGameDTO.getName()).getNextPlayer());


    }

}

