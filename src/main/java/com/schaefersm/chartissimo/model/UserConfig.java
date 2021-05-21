package com.schaefersm.chartissimo.model;

import java.util.HashMap;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userconfig")
public class UserConfig {

	@Id
	private String id;

	@DBRef()
	private User user;

	private HashMap<String, Integer> config = new HashMap<String, Integer>();

	public String getId() {
		return id;
	}

	public UserConfig() {

	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getConfig(String configKey) {
		return config.get(configKey);
	}
//
//	public void setConfig(String configKey, int configValue) {
//		this.config.put(configKey, configValue);
//	}

}