package com.schaefersm.auth.service;

import com.schaefersm.auth.exception.JwtTokenMalformedException;
import com.schaefersm.auth.exception.JwtTokenMissingException;
import com.schaefersm.auth.model.JwtToken;
import com.schaefersm.auth.repository.JwtRepository;
import com.schaefersm.auth.util.JwtUtil;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Log
public class RefreshService {

    private JwtRepository jwtRepository;

    private JwtUtil jwtUtil;

    @Autowired
    public RefreshService(JwtRepository jwtRepository, JwtUtil jwtUtil) {
        this.jwtRepository = jwtRepository;
        this.jwtUtil = jwtUtil;
    }

    public void checkToken(String token) {
        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(token);
        if(jwtToken.isPresent()) {
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


//    public void invalidateToken(String token) {
//        Claims body = jwtUtil.getRefreshClaims(token);
//        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(token);
//        if (jwtToken.isPresent()) {
//
//        }
//        long nowMillis = System.currentTimeMillis();
//        long expMillis = nowMillis + tokenValidity;
//        Date exp = new Date(expMillis);
//        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//
//    }

}
