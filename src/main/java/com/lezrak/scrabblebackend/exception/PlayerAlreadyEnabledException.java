package com.lezrak.scrabblebackend.exception;

public class PlayerAlreadyEnabledException  extends RuntimeException {
private String nickname;
    public PlayerAlreadyEnabledException( String nickname) {
        super(String.format("Nickname %s is already used.", nickname));
        this.nickname = nickname;
    }
}