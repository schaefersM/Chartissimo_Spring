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

}