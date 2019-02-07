package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public List<GameDTO> findAllByStartedFalse() {
        return GameMapper.toGameDTO(gameRepository.findByStartedFalse());
    }

    @Override
    public GameDTO makeMove(String gameName, Long playerId, HashMap<String, Character> move) {
        Game game = gameRepository.findByName(gameName);
        game.makeMove(playerId, move);
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    public GameDTO startGame(String gameName) {
        Game game = gameRepository.findByName(gameName);
        game.startGame();
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    public GameDTO findByName(String gameName) {
        return GameMapper.toGameDTO(gameRepository.findByName(gameName));
    }

    @Override
    public GameDTO addGame(Long playerId) {
        //TODO : random name builder
        return null;
    }

    @Override
    public GameDTO addPlayer(Long playerId, String gameName) {
        Game game = gameRepository.findByName(gameName);
        game.addPlayer(playerRepository.);
        return GameMapper.toGameDTO(gameRepository.save(game));
    }
}
