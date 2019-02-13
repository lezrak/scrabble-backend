package com.lezrak.scrabblebackend;

import com.lezrak.scrabblebackend.game.Game;
import com.lezrak.scrabblebackend.game.GameMapper;
import com.lezrak.scrabblebackend.game.GameRepository;
import com.lezrak.scrabblebackend.gameName.GameNameService;
import com.lezrak.scrabblebackend.player.Player;
import com.lezrak.scrabblebackend.player.PlayerRepository;
import com.lezrak.scrabblebackend.player.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrabbleBackendApplicationTests {
	@Autowired
	PlayerService playerService;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	GameNameService gameNameService;
	@Autowired
	GameRepository gameRepository;


	@Test
	public void contextLoads() {
		Player player = playerRepository.save(new Player("gracz1"));
		Game game = new Game(gameNameService.generateName());
		game.addPlayer(player);
		game = gameRepository.save(game);
		assert (playerService.getGames(player.getId())).contains(GameMapper.toGameDTO(game));



	}

}

