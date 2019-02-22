package com.lezrak.scrabblebackend.emailVerification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    java.util.Optional<VerificationToken> findOneByToken(String token);

    Set<VerificationToken> findAllByPlayerIsEnabledFalse();
}
