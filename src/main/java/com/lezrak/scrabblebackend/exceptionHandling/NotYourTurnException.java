package com.lezrak.scrabblebackend.exceptionHandling;

public class NotYourTurnException extends RuntimeException {

    public NotYourTurnException( ) {
        super("It's not your turn.");
    }
}
