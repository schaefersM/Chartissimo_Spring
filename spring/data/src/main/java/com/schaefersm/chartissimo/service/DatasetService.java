package com.schaefersm.chartissimo.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.schaefersm.chartissimo.repository.ArchitekturRepository;
import com.schaefersm.chartissimo.repository.InformatikRepository;
import com.schaefersm.chartissimo.repository.KostbarRepository;
import com.schaefersm.chartissimo.repository.WirtschaftRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetService {
    
    @Autowired
    private KostbarRepository kostbarRepository;

    @Autowired
    private InformatikRepository informatikRepository;

    @Autowired
    private ArchitekturRepository architekturRepository;

    @Autowired
    private WirtschaftRepository wirtschaftRepository;

    public Map<String, String> typeAbrevations = Map.ofEntries(
        Map.entry("comparison", "comp"),
        Map.entry("temperature", "temp"),
        Map.entry("humidity", "hum")
    );

    public List<Map<String, Integer>> processData(List<Integer[]> fetchedData, String timeValue) {
        List<Map<String, Integer>> processedData = new ArrayList<>();
        for (Integer[] rows : fetchedData) {
                Map<String, Integer> wirtschaftValue = new HashMap<>();
                wirtschaftValue.put(timeValue, rows[0]);
                wirtschaftValue.put("value", rows[1]);
                processedData.add(wirtschaftValue);
            }
        return processedData;
    }
    
    

    // public List<Integer[]> fetchData(Object repository, int hour, LocalDate datum, String host) {
    //     List<Integer[]> fetchedData = new ArrayList<>();
    //     List<Map<String, Integer>> processedData = new ArrayList<>();

    //     if (hour == 25) {
    //         return fetchedData = repository.findDailyDataset(host, datum);
            
    //     } else {
    //         return fetchedData = repository.findHouryDataset(host, datum, hour);
    //     }
    // }

    public String generateHost(String dataType, String location) {
        return String.format("%s_%s", location, typeAbrevations.get(dataType));
    }

    public Map<String, Object> getDatasets(String dataType, LocalDate datum, String location, int hour) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Integer>> processedData = new ArrayList<>();
        List<Integer[]> fetchedData = new ArrayList<>();
        String host = generateHost(dataType, location);
        switch (location) {
            case "wirtschaft": 
                if (hour == 25) {
                    fetchedData = wirtschaftRepository.findDailyDataset(host, datum);
                    processedData = processData(fetchedData, "hour");
                } else {
                    fetchedData = wirtschaftRepository.findHourlyDataset(host, datum, hour);
                    processedData = processData(fetchedData, "minute");
                }
            break;

            case "architektur": 
                if (hour == 25) {
                    fetchedData = architekturRepository.findDailyDataset(host, datum);
                    processedData = processData(fetchedData, "hour");
                } else {
                    fetchedData = architekturRepository.findHourlyDataset(host, datum, hour);
                    processedData = processData(fetchedData, "minute");
                }
            break;

            case "informatik":
                if (hour == 25) {
                    fetchedData = informatikRepository.findDailyDataset(host, datum);
                    processedData = processData(fetchedData, "hour");
                } else {
                    fetchedData = informatikRepository.findHourlyDataset(host, datum, hour);
                    processedData = processData(fetchedData, "minute");
                }
            break;

            
            case "kostbar":
                if (hour == 25) {
                    fetchedData = kostbarRepository.findDailyDataset(host, datum);
                    processedData = processData(fetchedData, "hour");
                } else {
                    fetchedData = kostbarRepository.findHourlyDataset(host, datum, hour);
                    processedData = processData(fetchedData, "minute");
                }
            break;
            
        }
        if (processedData.isEmpty()) {
            response.put("errorMessage", "No data available!");
            return response;
        }
        else {
            response.put("data", processedData);
            return response;
        }
    }

}
