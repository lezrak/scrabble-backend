package com.lezrak.scrabblebackend.game;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    public static GameDTO toGameDTO(Game game) {

        return new GameDTO(PlayerStateMapper.toPlayerStateDTO(game.getPlayers()),
                game.getBoardState(), game.getNextPlayer(), game.getName());
    }

    public static List<GameDTO> toGameDTO(List<Game> gameList) {
        return gameList.stream()
                .map(GameMapper::toGameDTO)
                .collect(Collectors.toList());
    }


}
