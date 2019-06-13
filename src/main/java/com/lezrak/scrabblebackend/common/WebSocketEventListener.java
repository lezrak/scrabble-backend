package com.lezrak.scrabblebackend.common;

import com.lezrak.scrabblebackend.game.GameServiceImpl;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private GameServiceImpl gameService;

    public WebSocketEventListener(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String[] playeridSessionidUuid = event.getSessionId().split(":");
        if(playeridSessionidUuid.length>1){
            this.gameService.securedRemovePlayer(Long.valueOf(playeridSessionidUuid[0]),playeridSessionidUuid[1]);
        }
    }
}