package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.game.GameDTO;

import javax.mail.MessagingException;
import java.util.List;

public interface PlayerService {

    boolean checkCredentials(PlayerDTO playerDTO);

    PlayerDTO postPlayer(PlayerDTO playerDTO) throws MessagingException;

    List<GameDTO> getGames(Long playerId);

    boolean nicknameIsUsed(String nickname);

    boolean emailIsUsed(String email);

}
