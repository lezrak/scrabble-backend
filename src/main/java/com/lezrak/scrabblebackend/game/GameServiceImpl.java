package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.exceptionHandling.GameNotFoundException;
import com.lezrak.scrabblebackend.exceptionHandling.PlayerNotFoundException;
import com.lezrak.scrabblebackend.exceptionHandling.PlayerNotInGameException;
import com.lezrak.scrabblebackend.gameName.GameNameService;
import com.lezrak.scrabblebackend.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public GameDTO createGame(Long playerId) {
        if (!playerRepository.existsPlayerById(playerId)) throw new PlayerNotFoundException(playerId.toString());
        Game game = new Game(gameNameService.generateName());
        game.addPlayer(playerRepository.findPlayerById(playerId));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }


    @Override
    public GameDTO addPlayer(Long playerId, String gameName) {
        validatePlayerAndGameInput(playerId, gameName);
        Game game = gameRepository.findByName(gameName);
        game.addPlayer(playerRepository.findPlayerById(playerId));
        playerRepository.save(playerRepository.findPlayerById(playerId).addGame(game));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    public void removePlayer(Long playerId, String gameName) {
        validatePlayerAndGameInput(playerId, gameName);
        if (gameRepository.findByName(gameName)
                .getPlayers()
                .stream()
                .map(PlayerState::getPlayer)
                .collect(Collectors.toSet())
                .contains(playerRepository.findPlayerById(playerId))) {
            playerRepository.save(playerRepository.findPlayerById(playerId).removeGame(gameRepository.findByName(gameName)));
            gameRepository.save(gameRepository.findByName(gameName).removePlayer(playerRepository.findPlayerById(playerId)));
        } else throw new PlayerNotInGameException(playerId.toString(), gameName);
    }

    @Override
    public GameDTO makeMove(String gameName, Long playerId, HashMap<String, Character> move) {
        validatePlayerAndGameInput(playerId, gameName);
        Game game = gameRepository.findByName(gameName);
        game.makeMove(playerId, move);
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    public GameDTO startGame(String gameName) {
        Game game = gameRepository.findByName(gameName);
        if (game == null) throw new GameNotFoundException(gameName);
        game.startGame();
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    private void validatePlayerAndGameInput(Long playerId, String gameName) {
        if (!playerRepository.existsPlayerById(playerId)) throw new PlayerNotFoundException(playerId.toString());
        Game game = gameRepository.findByName(gameName);
        if (game == null) throw new GameNotFoundException(gameName);
    }
}
