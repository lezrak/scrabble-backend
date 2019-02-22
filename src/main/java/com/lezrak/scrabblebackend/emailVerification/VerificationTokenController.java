package com.lezrak.scrabblebackend.emailVerification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification-token")
public class VerificationTokenController {
    private VerificationTokenService verificationTokenService;

    @Autowired
    public VerificationTokenController(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @CrossOrigin
    @GetMapping("/{token}")
    public void verify(@PathVariable String token) {
        verificationTokenService.verify(token);
    }
}
