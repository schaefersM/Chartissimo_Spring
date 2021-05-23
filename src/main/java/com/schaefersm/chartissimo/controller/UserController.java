package com.schaefersm.chartissimo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private MongoTemplate mongoTemplate;

//	@GetMapping("/{id}")
//	public Optional<User> getUserId(@PathVariable String id) {
//		System.out.println(id);
//		Optional<User> user = this.userRepository.findById(id);
//		System.out.println(user);
//		return user;
//	}

	@GetMapping("/{user}")
	public ResponseEntity<User> findUserById(@PathVariable("user") String user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(user));
		System.out.println(query);
		User userconfig = mongoTemplate.findOne(query, User.class);
		System.out.println(userconfig);
		return ResponseEntity.ok(userconfig);
	}

}
