package com.lezrak.scrabblebackend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/games")
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
    @GetMapping("/{name}")
    public GameDTO findByName(@PathVariable String name) {
        return gameService.findByName(name);
    }

    //todo: check authenticated nickname vs requested id
    @CrossOrigin
    @PostMapping
    public GameDTO createGame(@RequestParam Long playerId) {
        return gameService.createGame(playerId);
    }

    //todo: check authenticated nickname vs requested id
    @CrossOrigin
    @PatchMapping("/{name}/move")
    public GameDTO makeMove(@PathVariable String name,
                            @RequestBody HashMap<String, Character> move,
                            @RequestBody Long playerId) {
        return gameService.makeMove(name, playerId, move);
    }

    //todo: check authenticated nickname vs requested id
    @CrossOrigin
    @PatchMapping("/{name}/start")
    public GameDTO startGame(@PathVariable String name) {
        return gameService.startGame(name);
    }

    //todo: check authenticated nickname vs requested id
    @CrossOrigin
    @PatchMapping("/{name}/addPlayer")
    public GameDTO addPlayer(@RequestParam Long playerId, @PathVariable String name) {
        return gameService.addPlayer(playerId, name);
    }

    //todo: check authenticated nickname vs requested id
    @CrossOrigin
    @PatchMapping("/{name}/removePlayer")
    public void removePlayer(@RequestParam Long playerId, @PathVariable String name) {
        gameService.removePlayer(playerId, name);
    }

}
