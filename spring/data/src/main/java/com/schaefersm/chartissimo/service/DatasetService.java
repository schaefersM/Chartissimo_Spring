package com.schaefersm.chartissimo.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.schaefersm.chartissimo.Location;
import com.schaefersm.chartissimo.dto.DailyDatasetDTO;
import com.schaefersm.chartissimo.dto.DatasetDTO;
import com.schaefersm.chartissimo.dto.DatasetResponseDTO;
import com.schaefersm.chartissimo.dto.HourlyDatasetDTO;
import com.schaefersm.chartissimo.model.Dataset;
import com.schaefersm.chartissimo.repository.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasetService {
    
    private KostbarRepository kostbarRepository;

    private InformatikRepository informatikRepository;

    private ArchitekturRepository architekturRepository;

    private WirtschaftRepository wirtschaftRepository;

    private ModelMapper modelMapper;
    @Autowired
    public DatasetService(KostbarRepository kostbarRepository, InformatikRepository informatikRepository, ArchitekturRepository architekturRepository, WirtschaftRepository wirtschaftRepository, ModelMapper modelMapper) {
        this.kostbarRepository = kostbarRepository;
        this.informatikRepository = informatikRepository;
        this.architekturRepository = architekturRepository;
        this.wirtschaftRepository = wirtschaftRepository;
        this.modelMapper = modelMapper;
    }

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

    public DatasetResponseDTO getDatasets(String dataType, LocalDate date, String location, int hour) {
        DatasetResponseDTO datasetResponseDTO = new DatasetResponseDTO();
        try {
            if (dataType.equals("comparison")) {
                List<? extends DatasetDTO> tempDataset = fetchDatasets(String.format("%s_temp", location), date, location, hour);
                List<? extends DatasetDTO> humDataset = fetchDatasets(String.format("%s_hum", location), date, location, hour);
                datasetResponseDTO.addDataset(tempDataset);
                datasetResponseDTO.addDataset(humDataset);
            } else {
                String host = generateHost(dataType, location);
                List<? extends DatasetDTO> dataset = fetchDatasets(host, date, location, hour);
                datasetResponseDTO.addDataset(dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            datasetResponseDTO.setErrorMessage(e.getMessage());
        }
        return datasetResponseDTO;
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
