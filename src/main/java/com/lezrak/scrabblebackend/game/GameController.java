package com.lezrak.scrabblebackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public List<GameDTO> getLobby() {
        return gameService.findAllByStartedFalse();
    }

    @GetMapping("/games/{name}")
    public GameDTO findByName(@PathVariable String name) {
        return gameService.findByName(name);
    }

    @PostMapping("/games/")
    public GameDTO createGame(@RequestParam Long playerId) {
        return gameService.createGame(playerId);
    }

    @PatchMapping("/games/{name}/move")
    public GameDTO makeMove(@PathVariable String name,
                            @RequestBody HashMap<String, Character> move,
                            @RequestBody Long playerId) {
        return gameService.makeMove(name, playerId, move);
    }

    @PatchMapping("/games/{name}/start")
    public GameDTO startGame(@PathVariable String name) {
        return gameService.startGame(name);
    }

    @PatchMapping("/games/{name}/addPlayer")
    public GameDTO addPlayer(@RequestParam Long playerId, @PathVariable String name) {
        return gameService.addPlayer(playerId, name);
    }

    @PatchMapping("/games/{name}/removePlayer")
    public void removePlayer(@RequestParam Long playerId, @PathVariable String name) {
        gameService.removePlayer(playerId, name);
    }

}
