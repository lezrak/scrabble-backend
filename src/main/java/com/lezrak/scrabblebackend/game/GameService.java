package com.lezrak.scrabblebackend.game;

import java.util.List;
import java.util.Map;

public interface GameService {
    List<GameDTO> findAllByStartedFalse();
    GameDTO makeMove(String playerID, Map<String,Character> move);
    GameDTO startGame();
    GameDTO findByName(String name);
}
