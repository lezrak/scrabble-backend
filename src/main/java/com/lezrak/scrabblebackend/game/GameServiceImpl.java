package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.gameName.GameNameService;
import com.lezrak.scrabblebackend.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GameNameService gameNameService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, GameNameService gameNameService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gameNameService = gameNameService;
    }

    @Override
    public List<GameDTO> findAllByStartedFalse() {
        return GameMapper.toGameDTO(gameRepository.findByStartedFalse());
    }

    @Override
    public GameDTO findByName(String gameName) {
        return GameMapper.toGameDTO(gameRepository.findByName(gameName));
    }


    //TODO : exception throwing for addPlayer, remmovePlayer, makeMove, startGame and addGame

    @Override
    public GameDTO addGame(Long playerId) {
        Game game = new Game(gameNameService.generateName());
        game.addPlayer(playerRepository.findPlayerById(playerId));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }


    @Override
    public GameDTO addPlayer(Long playerId, String gameName) {
        Game game = gameRepository.findByName(gameName);
        game.addPlayer(playerRepository.findPlayerById(playerId));
        playerRepository.save(playerRepository.findPlayerById(playerId).addGame(game));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    public void removePlayer(Long playerId, String gameName) {
        gameRepository.save(gameRepository.findByName(gameName).removePlayer(playerRepository.findPlayerById(playerId)));
        playerRepository.save(playerRepository.findPlayerById(playerId).addGame(gameRepository.findByName(gameName)));
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
}
