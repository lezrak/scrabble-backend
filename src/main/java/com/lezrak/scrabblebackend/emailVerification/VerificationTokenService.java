package com.lezrak.scrabblebackend.emailVerification;

import com.lezrak.scrabblebackend.player.Player;

import javax.mail.MessagingException;

public interface VerificationTokenService {
     void verify(String token);

     void createToken(Player player) throws MessagingException;
}
