package com.schaefersm.chartissimo.model;

import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "userconfig")
public class UserConfig {

	@Id
	private String _id;

	private String user;

	private Map<String, Integer> config;

	public UserConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Map<String, Integer> getConfig() {
		return config;
	}

}