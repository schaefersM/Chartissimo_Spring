package com.schaefersm.chartissimo.service;


import java.time.LocalDate;
import java.util.ArrayList;
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

    private final int RESET_HOUR = 25;

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


    public List<? extends DatasetDTO> fetchDatasets(String host, LocalDate date, String location, int hour) throws Exception {
        List<? extends Dataset> fetchedData = new ArrayList<>();
        switch (Location.valueOf(location.toUpperCase(Locale.ROOT))) {
            case ARCHITEKTUR:
                if (hour == RESET_HOUR) {
                    fetchedData = architekturRepository.findAllByHostAndDatumAndMinute(host, date);
                } else {
                    fetchedData = architekturRepository.findAllByHostAndDatumAndHour(host, date, hour);
                }
                break;
            case INFORMATIK:
                if (hour == RESET_HOUR) {
                    fetchedData = informatikRepository.findAllByHostAndDatumAndMinute(host, date);
                } else {
                    fetchedData = informatikRepository.findAllByHostAndDatumAndHour(host, date, hour);
                }
                break;
            case KOSTBAR:
                if (hour == RESET_HOUR) {
                    fetchedData = kostbarRepository.findAllByHostAndDatumAndMinute(host, date);
                } else {
                    fetchedData = kostbarRepository.findAllByHostAndDatumAndHour(host, date, hour);
                }
                break;
            case WIRTSCHAFT:
                if (hour == RESET_HOUR) {
                    fetchedData = wirtschaftRepository.findAllByHostAndDatumAndMinute(host, date);
                } else {
                    fetchedData = wirtschaftRepository.findAllByHostAndDatumAndHour(host, date, hour);
                }
                break;
            default:
                break;
        }
        if (fetchedData.isEmpty()) {
            throw new Exception("No data available");
        }
        if (hour == 25) {
            return fetchedData.stream().map(dataset -> modelMapper.map(dataset, DailyDatasetDTO.class))
                    .collect(Collectors.toList());
        } else {
            return fetchedData.stream().map(dataset -> modelMapper.map(dataset, HourlyDatasetDTO.class))
                    .collect(Collectors.toList());
        }
    }

}
