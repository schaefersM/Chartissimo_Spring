package com.schaefersm.chartissimo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HourlyDatasetDTO extends DatasetDTO implements Serializable {

    private int minute;
}
