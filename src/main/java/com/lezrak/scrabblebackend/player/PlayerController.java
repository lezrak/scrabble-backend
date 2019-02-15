package com.lezrak.scrabblebackend.player;


import com.lezrak.scrabblebackend.game.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players/checkCredentials")
    public boolean checkCredentials(PlayerDTO playerDTO) {
        return playerService.checkCredentials(playerDTO);
    }

    @PostMapping("/players")
    public PlayerDTO postPlayer(PlayerDTO playerDTO) {
        return playerService.postPlayer(playerDTO);
    }

    @GetMapping("/players/{id}/games")
    public List<GameDTO> getGames(@PathVariable Long id) {
        return playerService.getGames(id);
    }

}
