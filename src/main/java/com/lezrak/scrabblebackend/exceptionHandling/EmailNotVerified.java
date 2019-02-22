package com.lezrak.scrabblebackend.exceptionHandling;

public class EmailNotVerified extends RuntimeException {
    private String mail;

    public EmailNotVerified(String mail) {
        super(String.format("Email %s is not verified. Please check inbox for verification link.", mail));
        this.mail = mail;
    }
}