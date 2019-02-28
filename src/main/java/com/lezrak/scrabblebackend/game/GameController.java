package com.lezrak.scrabblebackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @CrossOrigin
    @GetMapping
    public List<GameDTO> getLobby() {
        return gameService.findAllByStartedFalse();
    }

    @CrossOrigin
    @GetMapping("/{gameName}")
    public GameDTO findByName(@PathVariable String gameName) {
        return gameService.findByName(gameName);
    }

    @CrossOrigin
    @PostMapping
    public GameDTO createGame(@RequestParam Long playerId) {
        return gameService.createGame(playerId);
    }

    @CrossOrigin
    @PatchMapping("/{gameName}/move")
    public GameDTO makeMove(@PathVariable String gameName,
                            @RequestBody HashMap<String, Character> move,
                            @RequestBody Long playerId) {
        return gameService.makeMove(gameName, playerId, move);
    }

    @CrossOrigin
    @PatchMapping("/{gameName}/start")
    public GameDTO startGame(@PathVariable String gameName) {
        return gameService.startGame(gameName);
    }

    @CrossOrigin
    @PatchMapping("/{gameName}/addPlayer")
    public GameDTO addPlayer(@RequestParam Long playerId, @PathVariable String gameName) {
        return gameService.addPlayer(playerId, gameName);
    }

    @CrossOrigin
    @PatchMapping("/{gameName}/removePlayer")
    public void removePlayer(@RequestParam Long playerId, @PathVariable String gameName) {
        gameService.removePlayer(playerId, gameName);
    }

}
