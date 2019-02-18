package com.lezrak.scrabblebackend.exceptionHandling;

public class PlayerNotFoundException extends RuntimeException {
    private String name;

    public PlayerNotFoundException(String name) {
        super(String.format("Player %s does not exist.", name));
        this.name = name;
    }
}
