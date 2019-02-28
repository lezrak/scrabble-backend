package com.lezrak.scrabblebackend.exception;

public class EmailInUseException extends RuntimeException {
    private String email;

    public EmailInUseException(String email) {
        super(String.format("Email %s is already used.", email));
        this.email = email;
    }
}
