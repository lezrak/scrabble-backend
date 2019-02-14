package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.player.PlayerMapper;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class PlayerStateMapper {

    public static GameDTO.PlayerStateDTO toPlayerStateDTO(PlayerState playerState) {
        return new GameDTO.PlayerStateDTO(PlayerMapper.toPlayerDTO(playerState.getPlayer()), playerState.getTotalPoints(),
                playerState.getLastMovePoints(), playerState.getCharacters());
    }

    public static LinkedHashSet<GameDTO.PlayerStateDTO> toPlayerStateDTO(LinkedHashSet<PlayerState> playerStateSet) {
        return playerStateSet.stream()
                .map(PlayerStateMapper::toPlayerStateDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));

    }

}