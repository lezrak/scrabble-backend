package com.lezrak.scrabblebackend.emailVerification;

import com.lezrak.scrabblebackend.player.Player;
import com.lezrak.scrabblebackend.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final PlayerRepository playerRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository,
                                        PlayerRepository playerRepository, JavaMailSender javaMailSender) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.playerRepository = playerRepository;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void verify(String token) {
        verificationTokenRepository.findOneByToken(token)
                .ifPresent(t -> {
                    if (t.getExpirationDate().isAfter(LocalDateTime.now())) {
                        t.getPlayer().enable();
                        playerRepository.save(t.getPlayer());
                    }
                });
    }

    @Override
    public void createToken(Player player) throws MessagingException {
        VerificationToken token = new VerificationToken();
        token.setExpirationDate(LocalDateTime.now().plusMinutes(60));
        token.setToken(UUID.randomUUID().toString());
        token.setPlayer(player);
        verificationTokenRepository.save(token);
        sendEmail(player.getEmail(), token.getToken());
    }

    private void sendEmail(String email, String token) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(email);
        helper.setSubject("Email confirmation");
        helper.setText("Please confirm email registration by clicking the link:\nhttps://scrabble-ai-mock.herokuapp.com/verification-token/" + token.toString());
        javaMailSender.send(mail);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clearExpiredTokens() {
        Set<VerificationToken> tokens = verificationTokenRepository.findAllByPlayerIsEnabledFalse();
        final LocalDateTime now = LocalDateTime.now();
        Set<VerificationToken> expiredTokens = tokens.stream()
                .filter(token -> token.getExpirationDate().isBefore(now))
                .collect(toSet());
        verificationTokenRepository.deleteAll(expiredTokens);
    }
}
