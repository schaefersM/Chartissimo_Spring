package com.schaefersm.chartissimo.controller;

import java.util.HashMap;

import com.schaefersm.chartissimo.model.UserConfig;
import com.schaefersm.chartissimo.service.UserConfigService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost")
public class UserConfigController {

	@Autowired
	private UserConfigService userConfigService;

	@GetMapping("/{userId}/config")
	public ResponseEntity<HashMap<String, Object>> readUserConfig(@PathVariable("userId") ObjectId userId) {
		HashMap<String, Object> userConfig = userConfigService.getUserConfig(userId);
		if (userConfig != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userConfig);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/{userId}/config")
	public ResponseEntity<UserConfig> updateUserConfig(@PathVariable("userId") ObjectId userId,
			@RequestBody HashMap<String, HashMap<String, Object>> userConfig) {
		UserConfig userconfig = userConfigService.updateUserConfig(userId, userConfig);
		if (userconfig != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userconfig);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/{userId}/config")
	public ResponseEntity<UserConfig> createUserConfig(@PathVariable("userId") ObjectId userId,
			@RequestBody HashMap<String, HashMap<String, Object>> userConfig) {
		UserConfig userconfig = userConfigService.createUserConfig(userId, userConfig);
		if (userconfig != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(userconfig);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

}
