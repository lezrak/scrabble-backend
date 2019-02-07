package com.lezrak.scrabblebackend.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameService {
    List<GameDTO> findAllByStartedFalse();

    GameDTO makeMove(String gameName, Long playerId, HashMap<String, Character> move);

    GameDTO startGame(String gameName);

    GameDTO findByName(String gameName);

    GameDTO addGame(Long playerId);

    GameDTO addPlayer(Long playerId, String gameName);

    void removePlayer(Long playerId, String gameName);
}
