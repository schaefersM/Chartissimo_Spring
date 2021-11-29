package com.schaefersm.chartissimo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.schaefersm.chartissimo.model.UserChart;
import com.schaefersm.chartissimo.model.UserCharts;
import com.schaefersm.chartissimo.service.UserChartService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserChartController {

	private UserChartService userChartService;

	@Autowired
	public UserChartController(UserChartService userChartService) {
		this.userChartService = userChartService;
	}

	@GetMapping("/{userId}/charts")
	public ResponseEntity<UserCharts> getAllCharts(
		@PathVariable("userId") ObjectId userId, 
		@RequestParam(defaultValue = "-1") int page,
		@RequestParam(defaultValue = "1") int size,
		@RequestParam(required = false) List<String> location,
		@RequestParam(required = false) List<String> type,
		HttpServletRequest request) {
		UserCharts userCharts = userChartService.readAllCharts(userId, page, size, location, type, request);
		return ResponseEntity.status(HttpStatus.OK).body(userCharts);
	}

	@GetMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<UserChart> getOneChart(@PathVariable("userId") ObjectId userId,
			@PathVariable("chartId") Double chartId) {
		UserChart userChart = userChartService.readOneChart(userId, chartId);
		return ResponseEntity.status(HttpStatus.OK).body(userChart);
	}

	@PostMapping("/{userId}/charts")
	public ResponseEntity<Map<String, Object>> addOneChart(@PathVariable("userId") ObjectId userId,
			@RequestBody UserChart userChart) {
		userChartService.createOneChart(userId, userChart);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<Map<String, Object>> updateOneChart(@PathVariable("userId") ObjectId userId, 
			@PathVariable("chartId") Double chartId,
			@RequestBody UserChart userChart) {
		userChartService.updateOneChart(userId, chartId, userChart);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<?> deleteOneChart(@PathVariable("userId") ObjectId userId,
			@PathVariable("chartId") Double chartId, @RequestBody Map<String, Object> body) {
		userChartService.deleteOneChart(userId, chartId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
