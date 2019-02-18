package com.lezrak.scrabblebackend.exceptionHandling;

public class GameFullException extends RuntimeException {
    private String name;

    public GameFullException(String name) {
        super(String.format("Game %s is already full.", name));
        this.name = name;
    }
}