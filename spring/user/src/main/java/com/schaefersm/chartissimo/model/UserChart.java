package com.schaefersm.chartissimo.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usercharts")
public class UserChart {

	@Id
	private String _id;
	private ObjectId user;
	private String name;
	private Double id;
	private String chartType;
	private List<String> hosts;
	private Map<String, Object> data;
	private Map<String, Integer> customOptions;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime lastModified = LocalDateTime.now();
	private String image;


	@Override
	public String toString() {

		return String.format("%s %s", getChartType(), getHosts());
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public List<String> getHosts() {
		return hosts;
	}

	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public Map<String, Integer> getCustomOptions() {
		return customOptions;
	}

	public void setCustomOptions(Map<String, Integer> customOptions) {
		this.customOptions = customOptions;
	}

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified() {
		this.lastModified = LocalDateTime.now();;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectId getUser() {
		return user;
	}

	public void setUser(ObjectId user) {
		this.user = user;
	}

}
