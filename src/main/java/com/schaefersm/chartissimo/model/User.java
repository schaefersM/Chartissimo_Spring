package com.schaefersm.chartissimo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

	private String name;
	private String email;

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public User() {

	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}
}
