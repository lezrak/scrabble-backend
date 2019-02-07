package com.lezrak.scrabblebackend.player;

public interface PlayerService {
    boolean checkCredentials(String nickname);
    boolean checkCredentials(String nickname, String password);
    PlayerDTO addPlayer(PlayerDTO playerDTO);
}
