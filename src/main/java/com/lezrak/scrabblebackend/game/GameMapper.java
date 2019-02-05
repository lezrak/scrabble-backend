package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.player.PlayerMapper;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class GameMapper {

    public static GameDTO toGameDTO(Game game) {

        return new GameDTO(PlayerStateMapper.toPlayerStateDTO(game.getPlayers()),
                game.getBoardState(), game.getNextPlayer(), game.getName());
    }

    private static class PlayerStateMapper {

        static GameDTO.PlayerStateDTO toPlayerStateDTO(Game.PlayerState playerState) {
            return new GameDTO.PlayerStateDTO(PlayerMapper.toPlayerDTO(playerState.getPlayer()), playerState.getTotalPoints(),
                    playerState.getLastMovePoints(), playerState.getCharacters());
        }

        static LinkedHashSet<GameDTO.PlayerStateDTO> toPlayerStateDTO(LinkedHashSet<Game.PlayerState> playerStateSet) {
            return playerStateSet.stream()
                    .map(PlayerStateMapper::toPlayerStateDTO)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

        }

    }

}
