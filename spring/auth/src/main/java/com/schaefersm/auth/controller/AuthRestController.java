package com.schaefersm.auth.controller;

import com.schaefersm.auth.model.AuthenticationRequest;
import com.schaefersm.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/auth/login")
	public ResponseEntity<String> login(@RequestBody AuthenticationRequest request) {
		String token = jwtUtil.generateToken(request.getUsername());

		return new ResponseEntity<String>(token, HttpStatus.OK);
	}


}