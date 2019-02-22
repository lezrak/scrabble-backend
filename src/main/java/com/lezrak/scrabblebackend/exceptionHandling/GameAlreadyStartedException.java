package com.lezrak.scrabblebackend.exceptionHandling;

public class GameAlreadyStartedException extends RuntimeException {
private String game;
    public GameAlreadyStartedException(String game ) {
        super(String.format("Game %s is already started.", game));
        this.game = game;
    }
}