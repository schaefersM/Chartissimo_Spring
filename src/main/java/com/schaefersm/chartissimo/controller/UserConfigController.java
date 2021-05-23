package com.schaefersm.chartissimo.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private MongoTemplate mongoTemplate;

	public UserConfigController(UserConfigRepository userConfigRepository) {
		this.userConfigRepository = userConfigRepository;
	}

	@GetMapping("/{userId}/config")
	public ResponseEntity<UserConfig> findById(@PathVariable("userId") ObjectId userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user").is(userId));
		System.out.println(query);
		UserConfig userconfig = mongoTemplate.findOne(query, UserConfig.class);
		if (userconfig != null) {
			return ResponseEntity.ok(userconfig);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}
