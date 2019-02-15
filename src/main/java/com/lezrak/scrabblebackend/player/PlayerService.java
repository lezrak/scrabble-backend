package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.game.GameDTO;

import java.util.List;

public interface PlayerService {

    boolean checkCredentials(PlayerDTO playerDTO);

    PlayerDTO postPlayer(PlayerDTO playerDTO);

    List<GameDTO> getGames(Long playerId);

    boolean nicknameIsUsed(String nickname);

    boolean emailIsUsed(String email);

}
