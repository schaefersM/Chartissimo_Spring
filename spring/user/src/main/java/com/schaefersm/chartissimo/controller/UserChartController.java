package com.schaefersm.chartissimo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.schaefersm.chartissimo.model.UserChart;
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

	@Autowired
	private UserChartService userChartService;

	@GetMapping("/{userId}/charts")
	public ResponseEntity<Map<String, Object>> getAllCharts(
		@PathVariable("userId") ObjectId userId, 
		@RequestParam(defaultValue = "-1") int page,
		@RequestParam(defaultValue = "1") int size,
		@RequestParam(required = false) List<String> location,
		@RequestParam(required = false) List<String> type,
		HttpServletRequest request) {
		Map<String, Object> userCharts = userChartService.readAllCharts(userId, page, size,  location, type, request);
		if (userCharts != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userCharts);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<UserChart> getOneChart(@PathVariable("userId") ObjectId userId,
			@PathVariable("chartId") Double chartId) {
		UserChart userChart = userChartService.readOneChart(userId, chartId);
		if (userChart != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userChart);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/{userId}/charts")
	public ResponseEntity<Map<String, Object>> addOneChart(@PathVariable("userId") ObjectId userId,
			UserChart userChart) {
		Map<String, Object> response = userChartService.createOneChart(userId, userChart);
		if (response.get("errorMessage") == null && response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PutMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<Map<String, Object>> updateOneChart(@PathVariable("userId") ObjectId userId, 
			@PathVariable("chartId") Double chartId,
			@RequestBody UserChart userChart) {
		Map<String, Object> response = userChartService.updateOneChart(userId, chartId, userChart);
		if (response.get("errorMessage") == null && response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@DeleteMapping("/{userId}/charts/{chartId}")
	public ResponseEntity<Map<String, Object>> deleteOneChart(@PathVariable("userId") ObjectId userId,
			@PathVariable("chartId") Double chartId, @RequestBody Map<String, Object> body) {
		Map<String, Object> response = userChartService.deleteOneChart(userId, chartId, body);
		if (response.get("errorMessage") == null && response != null) {
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
