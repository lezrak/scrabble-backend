package com.lezrak.scrabblebackend.common;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void pokeLobby(@DestinationVariable String lobbyName, SocketMessage message) {
        this.template.convertAndSend("/lobby/"+lobbyName.toLowerCase(), message);
    }


    public void updateGame(@DestinationVariable String gameName, SocketMessage message) {
        this.template.convertAndSend("/game/"+gameName.toLowerCase(), message);
    }
}

