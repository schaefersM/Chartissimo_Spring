package com.schaefersm.chartissimo.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schaefersm.chartissimo.model.UserConfig;
import com.schaefersm.chartissimo.repository.UserConfigRepository;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin
public class UserConfigController {

	private UserConfigRepository userConfigRepository;

	public UserConfigController(UserConfigRepository userConfigRepository) {
		this.userConfigRepository = userConfigRepository;
	}

	@GetMapping("/{userId}/config")
	public Optional<UserConfig> getUserConfig(@PathVariable String userId) {
		System.out.println(userId);
		Optional<UserConfig> userConfig = this.userConfigRepository.findByUserId(userId);
		return userConfig;
	}
}
