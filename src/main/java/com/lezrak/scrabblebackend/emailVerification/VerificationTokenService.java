package com.lezrak.scrabblebackend.emailVerification;

import com.lezrak.scrabblebackend.player.Player;

import javax.mail.MessagingException;

public interface VerificationTokenService {
    public void verify(String token);

    public void createToken(Player player) throws MessagingException;
}
