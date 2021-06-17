package com.schaefersm.chartissimo.repository;

import org.springframework.data.repository.CrudRepository;

import com.schaefersm.chartissimo.model.UserChart;

public interface UserChartRepository extends CrudRepository<UserChart, String> {

}
