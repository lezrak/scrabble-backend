package com.lezrak.scrabblebackend.exception;

public class NotYourTurnException extends RuntimeException {

    public NotYourTurnException( ) {
        super("It's not your turn.");
    }
}
