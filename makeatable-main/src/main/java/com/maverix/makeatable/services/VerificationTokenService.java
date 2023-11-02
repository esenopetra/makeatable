package com.maverix.makeatable.services;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.exceptions.TokenNotFoundException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.models.VerificationToken;
import com.maverix.makeatable.repositories.UserRepository;
import com.maverix.makeatable.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenService {
    @Value("${verification.token.expiryHours}")
    private int expiryHours;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;



    public VerificationTokenService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken generateVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(expiryHours));
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }


    public User verifyUser(String token) {
        VerificationToken verificationToken = (VerificationToken) verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Verification token not found"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            verificationTokenRepository.delete(verificationToken);
            return null;
        }

        User user = verificationToken.getUser();
        user.setUserStatus(UserStatus.VERIFIED);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);

        return user;
    }
}
