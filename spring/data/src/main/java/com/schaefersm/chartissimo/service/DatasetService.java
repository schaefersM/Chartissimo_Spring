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

    public String generateHost(String dataType, String location) {
        return String.format("%s_%s", location, typeAbrevations.get(dataType));
    }

    public Map<String, Object> getDatasets(String dataType, LocalDate date, String location, int hour) {
        Map<String, Object> response = new HashMap<>();
        if (dataType.equals("comparison")) {
            List<List<Map<String, Integer>>> datasetList = new ArrayList<>();
            List<Map<String, Integer>> tempDataset = getFetchedDatasets(String.format("%s_temp", location), date, location, hour);
            datasetList.add(tempDataset);
            List<Map<String, Integer>> humDataset = getFetchedDatasets(String.format("%s_hum", location), date, location, hour);
            datasetList.add(humDataset);
            response.put("data", datasetList);
            return response;
        } else {
            String host = generateHost(dataType, location);
            List<Map<String, Integer>> realDataset = getFetchedDatasets(host, date, location, hour);
            response.put("data", realDataset);
            return response;
        }
    }

    public List<Map<String, Integer>> getFetchedDatasets(String host, LocalDate date, String location, int hour) {
        List<Map<String, Integer>> processedData = new ArrayList<>();
        List<Integer[]> fetchedData = new ArrayList<>();
        switch (location) {
            case "wirtschaft": 
                if (hour == 25) {
                    fetchedData = wirtschaftRepository.findDailyDataset(host, date);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "hour");
                } else {
                    fetchedData = wirtschaftRepository.findHourlyDataset(host, date, hour);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "minute");
                }
            break;

            case "architektur": 
                if (hour == 25) {
                    fetchedData = architekturRepository.findDailyDataset(host, date);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "hour");
                } else {
                    fetchedData = architekturRepository.findHourlyDataset(host, date, hour);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "minute");
                }
            break;

            case "informatik":
                if (hour == 25) {
                    fetchedData = informatikRepository.findDailyDataset(host, date);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "hour");
                } else {
                    fetchedData = informatikRepository.findHourlyDataset(host, date, hour);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "minute");
                }
            break;

            
            case "kostbar":
                if (hour == 25) {
                    fetchedData = kostbarRepository.findDailyDataset(host, date);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "hour");
                } else {
                    fetchedData = kostbarRepository.findHourlyDataset(host, date, hour);
                    processedData = fetchedData.isEmpty() ? new ArrayList<>() : processData(fetchedData, "minute");
                }
            break;
            
        }
        if (processedData.isEmpty()) {
            return null;
        }
        else {
            return processedData;
        }
    }

}
