package com.schaefersm.chartissimo.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schaefersm.chartissimo.model.UserChart;
import com.schaefersm.chartissimo.repository.UserChartRepository;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost")
public class UserChartController {

	private UserChartRepository userChartRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public UserChartController(UserChartRepository userChartRepository) {
		this.userChartRepository = userChartRepository;
	}

//	@GetMapping("/{userId}/charts")
//	public Iterable<UserChart> getAllCharts(@PathVariable ObjectId userId) {
//		Iterable<UserChart> userCharts = this.userChartRepository.findAll();
//		return userCharts;
//	}

	@GetMapping("/{userId}/charts")
	public ResponseEntity<List<UserChart>> getAllCharts(@PathVariable("userId") ObjectId userId) {
		List<UserChart> userCharts = mongoTemplate.findAll(UserChart.class);
		if (userCharts != null) {
			return ResponseEntity.ok(userCharts);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<UserChart> getOneChart(@PathVariable("userId") ObjectId userId,
			@PathVariable("chartId") Double chartId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("chartId").is(chartId));
		query.fields().exclude("image");
		System.out.println(query);
		UserChart userChart = mongoTemplate.findOne(query, UserChart.class);
		if (userChart != null) {
			return ResponseEntity.ok(userChart);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{userId}/charts")
	public void addOneChart(@PathVariable("userId") ObjectId userId,
//			@RequestBody Map<String, Object> body) {
			@RequestBody UserChart userChart) {
		System.out.println(userChart.getName());
		mongoTemplate.insert(userChart, "userchart");
	}

//	@PostMapping("/{userId}/charts")
//	public ResponseEntity<UserChart> addOneChart(@PathVariable("userId") ObjectId userId,
////			@RequestBody Map<String, Object> body) {
//			@RequestBody UserChart userChart) {
//		System.out.println(userChart.getName());
//		mongoTemplate.insert(null, null)
////		System.out.println(body.get("name"));
//		Query query = new Query();
//		System.out.println(query);
//		UserChart newUserChart = mongoTemplate.findOne(query, UserChart.class);
//		if (newUserChart != null) {
//			ResponseEntity.accepted().build();
//			return ResponseEntity.ok(newUserChart);
//		} else {
//			return ResponseEntity.noContent().build();
//		}
//	}
}
