package com.lezrak.scrabblebackend.common;

public class EmailInUseException extends RuntimeException {
    private String email;

    public EmailInUseException(String email) {
        super(String.format("Email %s is already used.", email));
        this.email = email;
    }
}
