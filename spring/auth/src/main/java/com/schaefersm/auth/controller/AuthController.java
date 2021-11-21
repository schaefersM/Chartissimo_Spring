package com.schaefersm.auth.controller;

import com.schaefersm.auth.dto.AuthResponseDTO;
import com.schaefersm.auth.model.*;
import com.schaefersm.auth.repository.JwtRepository;
import com.schaefersm.auth.repository.UserRepository;
import com.schaefersm.auth.service.LoginService;
import com.schaefersm.auth.service.RefreshService;
import com.schaefersm.auth.service.RegisterService;
import com.schaefersm.auth.util.CookieUtil;
import com.schaefersm.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Log
@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RefreshService refreshService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        User user = loginService.login(request.getName(), request.getPassword());
        String accessToken = jwtUtil.generateAccessToken(request.getName());
        String refreshToken = jwtUtil.generateRefreshToken(request.getName());
        jwtRepository.save(new JwtToken(jwtUtil.getRefreshClaims(refreshToken).getExpiration(), refreshToken));
        Cookie accessCookie = cookieUtil.getAccessCookie(accessToken);
        Cookie refreshCookie = cookieUtil.getRefreshCookie(refreshToken);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        AuthResponseDTO authResponseDTO = modelMapper.map(user, AuthResponseDTO.class);
        log.info(String.format("User %s logged in successfully!", user.getName()));
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(HttpServletResponse response, @CookieValue("refreshToken") String refreshToken) {
        refreshService.checkToken(refreshToken);
        Claims claims = jwtUtil.getRefreshClaims(refreshToken);
        log.info("Refreshing refreshToken of user " + claims.getSubject());
        String newRefreshToken = jwtUtil.generateRefreshToken(claims.getSubject(), claims.getExpiration());
        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(refreshToken);
        if (jwtToken.isPresent()) {
            jwtToken.get().setToken(newRefreshToken);
            jwtRepository.save(jwtToken.get());
        }
        String accessToken = jwtUtil.generateAccessToken(claims.getSubject());
        Cookie accessCookie = cookieUtil.getAccessCookie(accessToken);
        Cookie refreshCookie = cookieUtil.getRefreshCookie(newRefreshToken);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        User user = userRepository.findByName(claims.getSubject());
        AuthResponseDTO authResponseDTO = modelMapper.map(user, AuthResponseDTO.class);
        log.info("Everything's cool!");
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request) {
        registerService.register(request.getName(), request.getPassword(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@CookieValue("refreshToken") String refreshToken) {
        log.info("Logging out user...");
        Optional<JwtToken> jwtToken = jwtRepository.findJwtTokenByToken(refreshToken);
        if (jwtToken.isPresent()) {
            jwtRepository.delete(jwtToken.get());
            log.info("Jwt token deleted!");
        }
        log.info("User logged out successfully!");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}