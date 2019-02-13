package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.game.GameDTO;

import java.util.List;

public interface PlayerService {
    boolean checkCredentials(String nickname, String password);

    PlayerDTO addPlayer(PlayerDTO playerDTO);

    boolean nicknameIsUsed(String nickname);

    boolean emailIsUsed(String email);

    List<GameDTO> getGames(Long playerId);


}
