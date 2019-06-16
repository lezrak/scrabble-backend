package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.common.SocketMessage;
import com.lezrak.scrabblebackend.common.WebSocketController;
import com.lezrak.scrabblebackend.exception.GameNotFoundException;
import com.lezrak.scrabblebackend.exception.IdentityMismatchException;
import com.lezrak.scrabblebackend.exception.PlayerNotFoundException;
import com.lezrak.scrabblebackend.exception.PlayerNotInGameException;
import com.lezrak.scrabblebackend.gameName.GameNameService;
import com.lezrak.scrabblebackend.player.Player;
import com.lezrak.scrabblebackend.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.java2d.windows.GDIBlitLoops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GameNameService gameNameService;
    private WebSocketController webSocketController;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, GameNameService gameNameService, WebSocketController webSocketController) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gameNameService = gameNameService;
        this.webSocketController = webSocketController;
    }

    @Override
    public List<GameDTO> findAllByStartedFalse() {
        return GameMapper.toGameDTO(gameRepository.findByStartedFalse());
    }

    @Override
    public GameDTO findByName(String gameName) {
        if (!gameRepository.existsGameByName(gameName)) throw new GameNotFoundException(gameName);
        return GameMapper.toGameDTO(gameRepository.findByName(gameName));
    }


    @Override
    @Transactional
    public GameDTO createGame(Long playerId) {
        identityCheck(playerId);
        if (!playerRepository.existsPlayerById(playerId)) throw new PlayerNotFoundException(playerId.toString());
        Game game = new Game(gameNameService.generateName());
        playerRepository.save(playerRepository.findPlayerById(playerId).addGame(game));
        game.addPlayer(playerRepository.findPlayerById(playerId));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }


    @Override
    @Transactional
    public GameDTO addPlayer(Long playerId, String gameName) {
        identityCheck(playerId);
        validate(playerId, gameName);
        Game game = gameRepository.findByName(gameName);
        game.addPlayer(playerRepository.findPlayerById(playerId));
        playerRepository.save(playerRepository.findPlayerById(playerId).addGame(game));
        webSocketController.pokeLobby(gameName, new SocketMessage("CHANGE"));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    @Transactional
    public GameDTO addAI(String gameName) {
        Game game = gameRepository.findByName(gameName);
        Player ai = new Player("AI " + game.getPlayers().size() +" "+ gameName,true);
        game.addPlayer(ai);
        playerRepository.save(ai.addGame(game));
        webSocketController.pokeLobby(gameName, new SocketMessage("CHANGE"));
        return GameMapper.toGameDTO(gameRepository.save(game));
    }

    @Override
    @Transactional
    public void removePlayer(Long playerId, String gameName) {
        identityCheck(playerId);
        validate(playerId, gameName);
        if (gameRepository.findByName(gameName)
                .getPlayers()
                .stream()
                .map(PlayerState::getPlayer)
                .collect(Collectors.toSet())
                .contains(playerRepository.findPlayerById(playerId))) {
            playerRepository.save(playerRepository.findPlayerById(playerId).removeGame(gameRepository.findByName(gameName)));
            gameRepository.save(gameRepository.findByName(gameName).removePlayer(playerRepository.findPlayerById(playerId)));
            webSocketController.pokeLobby(gameName, new SocketMessage("CHANGE"));
        } else throw new PlayerNotInGameException(playerId.toString(), gameName);
    }

    public void securedRemovePlayer(Long playerId, String gameName) {
        Game game = gameRepository.findByName(gameName);
        if (!game.isStarted()) {
            validate(playerId, gameName);
            if (game
                    .getPlayers()
                    .stream()
                    .map(PlayerState::getPlayer)
                    .collect(Collectors.toSet())
                    .contains(playerRepository.findPlayerById(playerId))) {
                playerRepository.save(playerRepository.findPlayerById(playerId).removeGame(gameRepository.findByName(gameName)));
                gameRepository.save(gameRepository.findByName(gameName).removePlayer(playerRepository.findPlayerById(playerId)));
                if (gameRepository.findByName(gameName).getPlayers().size() == 0)
                    gameRepository.delete(gameRepository.findByName(gameName));
                webSocketController.pokeLobby(gameName, new SocketMessage("CHANGE"));
            } else throw new PlayerNotInGameException(playerId.toString(), gameName);
        }
    }

    @Override
    public String getHint(String gameName) {
        //todo should we do something with websocket here?
        return gameRepository.findByName(gameName).getHint();
    }

    @Override
    public GameDTO tradeLetters(String gameName, Long playerId, ArrayList<Character> characters) {
        identityCheck(playerId);
        validate(playerId, gameName);
        Game game = gameRepository.findByName(gameName);
        game.tradeLetters(playerId, characters);
        GameDTO gameDTO = GameMapper.toGameDTO(gameRepository.save(game));
        webSocketController.updateGame(gameName, new SocketMessage("hed", gameDTO));
        return gameDTO;
    }

    @Override
    @Transactional
    public GameDTO makeMove(String gameName, Long playerId, HashMap<String, Character> move) {
        identityCheck(playerId);
        validate(playerId, gameName);
        Game game = gameRepository.findByName(gameName);
        game.makeMove(playerId, move);
        GameDTO gameDTO = GameMapper.toGameDTO(gameRepository.save(game));
        webSocketController.updateGame(gameName, new SocketMessage("hed", gameDTO));
        return gameDTO;
    }

    @Override
    @Transactional
    public GameDTO startGame(String gameName) {
        Game game = gameRepository.findByName(gameName);
        if (game == null) throw new GameNotFoundException(gameName);
        game.startGame();
        GameDTO startedGame = GameMapper.toGameDTO(gameRepository.save(game));
        webSocketController.pokeLobby(gameName, new SocketMessage("START", startedGame));
        return startedGame;
    }

    private void validate(Long playerId, String gameName) {
        if (!playerRepository.existsPlayerById(playerId)) throw new PlayerNotFoundException(playerId.toString());
        if (!gameRepository.existsGameByName(gameName)) throw new GameNotFoundException(gameName);
    }

    private void identityCheck(Long playerId) {
        Long credentialsId = ((Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (!(credentialsId.equals(playerId))) {
            throw new IdentityMismatchException(credentialsId.toString(), playerId.toString());
        }
    }
}
