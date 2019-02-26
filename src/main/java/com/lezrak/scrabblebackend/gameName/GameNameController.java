package com.lezrak.scrabblebackend.gameName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(timeout = 1200)
    public void populate() {
        gameNameService.populate();
    }

}
