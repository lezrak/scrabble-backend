package com.lezrak.scrabblebackend.exception;

public class PlayerNotInGameException extends RuntimeException {
    private String player;
    private String game;

    public PlayerNotInGameException(String player, String game) {
        super(String.format("Player %s is not in game %s.", player, game));
        this.player = player;
        this.game = game;
    }
}