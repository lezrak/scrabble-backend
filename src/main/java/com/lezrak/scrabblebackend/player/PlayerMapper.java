package com.lezrak.scrabblebackend.player;

public class PlayerMapper {

    public static PlayerDTO toPlayerDTO(Player player) {
        return new PlayerDTO(player.getEmail(), player.getNickname(), player.getId());
    }


}
