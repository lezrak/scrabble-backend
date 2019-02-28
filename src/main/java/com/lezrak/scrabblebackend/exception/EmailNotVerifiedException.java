package com.lezrak.scrabblebackend.exception;

public class EmailNotVerifiedException extends RuntimeException {
    private String mail;

    public EmailNotVerifiedException(String mail) {
        super(String.format("Email %s is not verified. Please check inbox for verification link.", mail));
        this.mail = mail;
    }
}