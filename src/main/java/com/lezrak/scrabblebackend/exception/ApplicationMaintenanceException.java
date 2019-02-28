package com.lezrak.scrabblebackend.exception;

public class ApplicationMaintenanceException extends RuntimeException {

    public ApplicationMaintenanceException( ) {
        super("We are currently working to make your App better. Please try again in few minutes.");
    }
}