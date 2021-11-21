package com.schaefersm.auth.util;

import java.util.*;

import com.schaefersm.auth.exception.JwtTokenMalformedException;
import com.schaefersm.auth.exception.JwtTokenMissingException;

import com.schaefersm.auth.repository.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

    @Value("${secret.access}")
    private String accessSecret;

    @Value("${secret.refresh}")
    private String refreshSecret;

    @Value("${validity.refresh}")
    private long refreshValidity;

    @Value("${validity.access}")
    private long accessValidity;

    JwtRepository jwtRepository;

    @Autowired
    public JwtUtil(JwtRepository jwtRepository) {
        this.jwtRepository = jwtRepository;
    }

    public Claims getAccessClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(accessSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public Claims getRefreshClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }



    public String generateAccessToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * accessValidity;
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, accessSecret).compact();
    }

    public String generateRefreshToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * refreshValidity;
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, refreshSecret).compact();
    }

    public String generateRefreshToken(String id, Date expirationDate) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, refreshSecret).compact();
    }

//    public String generateMultiToken(String... values) {
//        String subjects = String.join(";", values);
//        Claims claims = Jwts.claims().setSubject(subjects);
//        long nowMillis = System.currentTimeMillis();
//        long expMillis = nowMillis + tokenValidity;
//        Date exp = new Date(expMillis);
//        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
//                .signWith(SignatureAlgorithm.HS512, accessSecret).compact();
//    }

//    public Map<String, String> getSubjectsFromMultiToken(String value) {
//        Map<String, String> subjects = new HashMap<>();
//        String[] splittedValues = value.split(";");
//        subjects.put("username", splittedValues[0]);
//        subjects.put("email", splittedValues[1]);
//        return subjects;
//    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("JWT claims string is empty.");
        }
    }

}