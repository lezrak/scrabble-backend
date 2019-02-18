package com.lezrak.scrabblebackend.exceptionHandling;

public class NicknameInUseException extends RuntimeException {
    private String nickname;

    public NicknameInUseException(String nickname) {
        super(String.format("Nickname %s is already used.", nickname));
        this.nickname = nickname;
    }
}
