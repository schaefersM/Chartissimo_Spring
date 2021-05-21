package com.schaefersm.chartissimo.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schaefersm.chartissimo.model.User;
import com.schaefersm.chartissimo.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin
public class UserController {

	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/{id}")
	public Optional<User> getUserId(@PathVariable String id) {
		System.out.println(id);
		Optional<User> user = this.userRepository.findById(id);
		System.out.println(user);
		return user;
	}

	@GetMapping("/{id}/{name}")
	public Optional<User> getUser(@PathVariable String name) {
		System.out.println(name);
		Optional<User> user = this.userRepository.findByName(name);
		System.out.println(user);
		return user;
	}

}
