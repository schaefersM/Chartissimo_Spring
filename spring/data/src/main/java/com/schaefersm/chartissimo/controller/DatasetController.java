package com.schaefersm.chartissimo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.schaefersm.chartissimo.service.DatasetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "http://localhost")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @GetMapping("/{dataType}")
	public ResponseEntity<Map<String, Object>> getDatasets(
		@PathVariable("dataType") String dataType, 
		@RequestParam(required = false) String date,
		@RequestParam(required = false) String location,
		@RequestParam(required = false) int hour) 
	{

		LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
		Map<String, Object> response = datasetService.getDatasets(dataType, parsedDate, location, hour);
		if (response.get("errorMessage") == null && response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
