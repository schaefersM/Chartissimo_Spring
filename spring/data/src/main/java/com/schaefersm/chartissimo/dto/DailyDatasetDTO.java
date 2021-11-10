package com.schaefersm.chartissimo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DailyDatasetDTO extends DatasetDTO implements Serializable{

    private int hour;

}
