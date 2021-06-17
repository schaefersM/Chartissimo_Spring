package com.schaefersm.chartissimo.model;

import java.util.HashMap;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "userconfig")
public class UserConfig {

	@Id
	private String _id;

	private ObjectId user;

	private HashMap<String, Object> config;

	public UserConfig() {

	}

	public UserConfig(String id, ObjectId user, HashMap<String, Object> config) {
		this._id = id;
		this.user = user;
		this.config = config;
	}

	public UserConfig(HashMap<String, Object> config, ObjectId user) {
		this.config = config;
		this.user = user;
	}


	public void setUser(ObjectId user) {
		this.user = user;
	}

	public HashMap<String, Object> getConfig() {
		return config;
	}

	public void setConfig(HashMap<String, Object> config) {
		this.config = config;
	}

}