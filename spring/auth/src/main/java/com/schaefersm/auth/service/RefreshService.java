package com.schaefersm.auth.service;

import com.schaefersm.auth.exception.JwtTokenMalformedException;
import com.schaefersm.auth.exception.JwtTokenMissingException;
import com.schaefersm.auth.exception.UserNotFoundException;
import com.schaefersm.auth.model.JwtToken;
import com.schaefersm.auth.model.User;
import com.schaefersm.auth.repository.JwtRepository;
import com.schaefersm.auth.repository.UserRepository;
import com.schaefersm.auth.util.CookieUtil;
import com.schaefersm.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log
public class RefreshService {

    private JwtRepository jwtRepository;
    private UserRepository userRepository;
    private CookieUtil cookieUtil;
    private JwtUtil jwtUtil;

    @Autowired
    public RefreshService(JwtRepository jwtRepository, UserRepository userRepository, CookieUtil cookieUtil, JwtUtil jwtUtil) {
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
        this.cookieUtil = cookieUtil;
        this.jwtUtil = jwtUtil;
    }

    public void checkToken(String token) {
        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(token);
        if (jwtToken.isPresent()) {
            try {
                log.info("Validating Refreshtoken...");
                jwtUtil.validateToken(token);
            } catch (ExpiredJwtException e) {
                jwtRepository.delete(jwtToken.get());
                log.info("Jwt Token expired!");
                throw new JwtTokenMalformedException("Jwt Token expired!");
            }
        } else {
            log.info("Jwt Token not found!");
            throw new JwtTokenMissingException("Jwt Token not found!");

        }
    }

    public User getUserDetails(String token) {
        Claims claims = jwtUtil.getRefreshClaims(token);
        User user = userRepository.findByName(claims.getSubject());
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public String generateNewRefreshToken(String refreshToken) {
        Claims claims = jwtUtil.getRefreshClaims(refreshToken);
        log.info("Refreshing refreshToken of user " + claims.getSubject());
        String newRefreshToken = jwtUtil.generateRefreshToken(claims.getSubject(), claims.getExpiration());
        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(refreshToken);
        if (jwtToken.isPresent()) {
            jwtToken.get().setToken(newRefreshToken);
            jwtRepository.save(jwtToken.get());
        }
        return newRefreshToken;
    }

    public Map<String, Cookie> generateCookies(String refreshToken, String newRefreshToken) {
        Map<String, Cookie> cookies = new HashMap<>();
        Claims claims = jwtUtil.getRefreshClaims(refreshToken);
        String accessToken = jwtUtil.generateAccessToken(claims.getSubject());
        Cookie accessCookie = cookieUtil.getAccessCookie(accessToken);
        Cookie refreshCookie = cookieUtil.getRefreshCookie(newRefreshToken);
        cookies.put("access", accessCookie);
        cookies.put("refresh", refreshCookie);
        return cookies;
    }


}
