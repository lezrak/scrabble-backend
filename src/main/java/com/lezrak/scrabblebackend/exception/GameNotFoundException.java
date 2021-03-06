package com.lezrak.scrabblebackend.exception;

public class GameNotFoundException extends RuntimeException {
    private String name;

    public GameNotFoundException(String name) {
        super(String.format("Game %s does not exist.", name));
        this.name = name;
    }
}
