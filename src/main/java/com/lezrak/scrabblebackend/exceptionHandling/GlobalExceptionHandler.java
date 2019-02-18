package com.lezrak.scrabblebackend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({EmailInUseException.class, GameFullException.class, GameNotFoundException.class,
            NicknameInUseException.class, PlayerNotFoundException.class, PlayerNotInGameException.class})
    @ResponseBody
    protected ResponseEntity<String> handleIncorrectRequestException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
