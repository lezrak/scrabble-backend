package com.lezrak.scrabblebackend.player;


import com.lezrak.scrabblebackend.game.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @PostMapping
    public PlayerDTO postPlayer(@RequestBody PlayerDTO playerDTO) throws MessagingException {
        return playerService.postPlayer(playerDTO);
    }

    @GetMapping("/{playerId}/games")
    public List<GameDTO> getGames(@PathVariable Long playerId) {
        return playerService.getGames(playerId);
    }

    @GetMapping("/{playerName}")
    public PlayerDTO getPlayerInfo (@PathVariable String playerName){
        return playerService.getPlayerInfo(playerName);
    }

}
