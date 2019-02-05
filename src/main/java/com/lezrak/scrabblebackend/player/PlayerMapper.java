package com.lezrak.scrabblebackend.player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerMapper {

    public static PlayerDTO toPlayerDTO(Player player){
        return new PlayerDTO(player.getEmail(),player.getNickname());
    }

    public static List<PlayerDTO> toPlayerDTO(List<Player> list){
        return list.stream()
                .map(PlayerMapper::toPlayerDTO)
                .collect(Collectors.toList());
    }
}
