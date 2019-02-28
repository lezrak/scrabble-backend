package com.lezrak.scrabblebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({EmailInUseException.class, GameFullException.class, GameNotFoundException.class,
            NicknameInUseException.class, PlayerNotFoundException.class, PlayerNotInGameException.class,
            NotYourTurnException.class, GameAlreadyStartedException.class,
            PlayerAlreadyEnabledException.class, EmailNotVerifiedException.class, IdentityMismatchException.class})
    @ResponseBody
    protected ResponseEntity<String> handleIncorrectRequestException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ApplicationMaintenanceException.class})
    @ResponseBody
    protected ResponseEntity<String> handleApplicationException(RuntimeException e) {
        return new ResponseEntity<>((e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
