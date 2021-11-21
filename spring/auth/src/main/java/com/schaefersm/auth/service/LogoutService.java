package com.schaefersm.auth.service;

import com.schaefersm.auth.model.JwtToken;
import com.schaefersm.auth.repository.JwtRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@Service
public class LogoutService {

    private JwtRepository jwtRepository;

    @Autowired
    public LogoutService(JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public void deleteJwtToken(String token) {
        log.info("Logging out user...");
        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(token);
        if (jwtToken.isPresent()) {
            jwtRepository.delete(jwtToken.get());
            log.info("Jwt token deleted!");
        }
        log.info("User logged out successfully!");
    }

}
