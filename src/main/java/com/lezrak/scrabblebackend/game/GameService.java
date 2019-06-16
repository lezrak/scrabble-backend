package com.lezrak.scrabblebackend.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface GameService {

    List<GameDTO> findAllByStartedFalse();

    GameDTO makeMove(String gameName, Long playerId, HashMap<String, Character> move);

    GameDTO tradeLetters(String gameName, Long playerId, ArrayList<Character> characters);

    GameDTO startGame(String gameName);

    GameDTO findByName(String gameName);

    GameDTO createGame(Long playerId);

    GameDTO addPlayer(Long playerId, String gameName);

    GameDTO addAI(String gameName);

    String getHint(String gameName);

    void removePlayer(Long playerId, String gameName);
}
