package com.lezrak.scrabblebackend.player;


import com.lezrak.scrabblebackend.game.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @CrossOrigin
    @PostMapping
    public PlayerDTO postPlayer(@RequestBody PlayerDTO playerDTO) throws MessagingException {
        return playerService.postPlayer(playerDTO);
    }

    @CrossOrigin
    @GetMapping("/{id}/games")
    public List<GameDTO> getGames(@PathVariable Long id) {
        return playerService.getGames(id);
    }

}
