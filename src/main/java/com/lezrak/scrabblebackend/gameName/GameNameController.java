package com.lezrak.scrabblebackend.gameName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameNameController {

    private final GameNameService gameNameService;

    @Autowired
    public GameNameController(GameNameService gameNameService) {
        this.gameNameService = gameNameService;
    }

    @CrossOrigin
    @PostMapping("/populate")
    public void populate() {
        gameNameService.populate();
    }

}
