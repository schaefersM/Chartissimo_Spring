package com.schaefersm.chartissimo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatasetResponseDTO extends BaseResponseDTO {

    private List<List<? extends DatasetDTO>> data = new ArrayList<>(new ArrayList<>());

    public void addDataset(List<? extends DatasetDTO> dataset) {
        this.data.add(dataset);
    }
}
