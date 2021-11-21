package com.schaefersm.auth.service;

import com.schaefersm.auth.exception.PasswordIsWrongException;
import com.schaefersm.auth.exception.UserNotFoundException;
import com.schaefersm.auth.model.AuthenticationRequest;
import com.schaefersm.auth.model.JwtToken;
import com.schaefersm.auth.model.User;
import com.schaefersm.auth.repository.JwtRepository;
import com.schaefersm.auth.repository.UserRepository;
import com.schaefersm.auth.util.CookieUtil;
import com.schaefersm.auth.util.JwtUtil;
import com.schaefersm.auth.util.ValidationUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

@Log
@Service
public class LoginService {

    private JwtRepository jwtRepository;
    private UserRepository userRepository;
    private CookieUtil cookieUtil;
    private JwtUtil jwtUtil;
    private ValidationUtil validationUtil;

    @Autowired
    public LoginService(JwtRepository jwtRepository, UserRepository userRepository, CookieUtil cookieUtil, JwtUtil jwtUtil, ValidationUtil validationUtil) {
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
        this.cookieUtil = cookieUtil;
        this.jwtUtil = jwtUtil;
        this.validationUtil = validationUtil;
    }

    public User getUserDetails(String name, String password) {
        validationUtil.validateUsername(name);
        validationUtil.validatePassword(password);
        User user = userRepository.findByName(name);
        log.info("Logging in user " + name + "...");
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.getPassword().equals(password)) {
            throw new PasswordIsWrongException();
        }
        return user;
    }

    public Map<String, Cookie> generateCookies(AuthenticationRequest request) {
        Map<String, Cookie> cookies = new HashMap<>();
        String accessToken = jwtUtil.generateAccessToken(request.getName());
        String refreshToken = jwtUtil.generateRefreshToken(request.getName());
        jwtRepository.save(new JwtToken(jwtUtil.getRefreshClaims(refreshToken).getExpiration(), refreshToken));
        Cookie accessCookie = cookieUtil.getAccessCookie(accessToken);
        Cookie refreshCookie = cookieUtil.getRefreshCookie(refreshToken);
        cookies.put("access", accessCookie);
        cookies.put("refresh", refreshCookie);
        return cookies;
    }

}
