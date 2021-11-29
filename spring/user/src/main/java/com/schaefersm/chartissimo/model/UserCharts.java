package com.schaefersm.chartissimo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserCharts {

    List<UserChart> results = new ArrayList<>();
    Links links;

}
