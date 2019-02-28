package com.lezrak.scrabblebackend.exception;

public class IdentityMismatchException  extends RuntimeException {
    private String credentialsId;
    private String requestId;

    public IdentityMismatchException(String credentialsId, String requestId) {
        super(String.format("Id %s passed with credentials doesn't match Id passed in request %s .", credentialsId, requestId));
        this.credentialsId = credentialsId;
        this.requestId = requestId;
    }
}
