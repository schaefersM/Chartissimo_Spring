package com.schaefersm.chartissimo.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Entity
@Document()
public class User {

	@Id
	private String _id;

	private String name;
	private String email;
	private LocalDate date;

	public String getId() {
		return _id;
	}

	public User() {

	}

	public User(String id, String name, String email, LocalDate date) {
		this._id = id;
		this.name = name;
		this.email = email;
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
