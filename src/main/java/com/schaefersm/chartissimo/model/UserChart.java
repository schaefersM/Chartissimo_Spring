package com.schaefersm.chartissimo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "userchart")
public class UserChart {

	@Id
	private String _id;
	private LocalDate createdAt;
	private Map<String, Object> data;
	private List<String> hosts;
	private String chartType;
	private Map<String, Integer> customOptions;
	@Field("id")
	private Double chartId;
	private String image;
	private LocalDate lastModified;
	private String name;
	private String user;

	@Override
	public String toString() {

		return String.format("%s %s %s", getName(), getUser(), get_id());
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
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

	public Double getChartId() {
		return chartId;
	}

	public void setChartId(Double chartId) {
		this.chartId = chartId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDate getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDate lastModified) {
		this.lastModified = lastModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
