package com.schaefersm.auth.controller;

import com.schaefersm.auth.dto.AuthResponseDTO;
import com.schaefersm.auth.model.*;
import com.schaefersm.auth.repository.JwtRepository;
import com.schaefersm.auth.repository.UserRepository;
import com.schaefersm.auth.service.LoginService;
import com.schaefersm.auth.service.LogoutService;
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
import java.util.Map;
import java.util.Optional;

@Log
@RestController
public class AuthController {

    private LoginService loginService;
    private LogoutService logoutService;
    private RefreshService refreshService;
    private RegisterService registerService;
    private ModelMapper modelMapper;

    @Autowired
    public AuthController(LoginService loginService, LogoutService logoutService, RefreshService refreshService, RegisterService registerService, ModelMapper modelMapper) {
        this.loginService = loginService;
        this.logoutService = logoutService;
        this.refreshService = refreshService;
        this.registerService = registerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        User user = loginService.getUserDetails(request.getName(), request.getPassword());
        Map<String, Cookie> cookies = loginService.generateCookies(request);
        response.addCookie(cookies.get("access"));
        response.addCookie(cookies.get("refresh"));
        AuthResponseDTO authResponseDTO = modelMapper.map(user, AuthResponseDTO.class);
        log.info(String.format("User %s logged in successfully!", user.getName()));
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(HttpServletResponse response, @CookieValue("refreshCookie") String refreshToken) {
        refreshService.checkToken(refreshToken);
        String newRefreshToken = refreshService.generateNewRefreshToken(refreshToken);
        Map<String, Cookie> cookies = refreshService.generateCookies(refreshToken, newRefreshToken);
        response.addCookie(cookies.get("access"));
        response.addCookie(cookies.get("refresh"));
        User user = refreshService.getUserDetails(refreshToken);
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
    public ResponseEntity<?> logout(@CookieValue("refreshCookie") String refreshToken) {
        logoutService.deleteJwtToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}