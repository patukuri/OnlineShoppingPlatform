package com.securecoding.onelineshoppingplatform.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securecoding.onelineshoppingplatform.Model.ConfirmationToken;
import com.securecoding.onelineshoppingplatform.repository.ConfirmationTokenRepo;



@Service

public class ConfirmationTokenService {

@Autowired
    private static  ConfirmationTokenRepo confirmationTokenRepository;

    public static void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
    public ConfirmationTokenService(ConfirmationTokenRepo confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

}
