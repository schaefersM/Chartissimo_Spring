package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Wirtschaft;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WirtschaftRepository extends CrudRepository<Wirtschaft, Integer> {
    @Query("SELECT w.hour, w.value from Wirtschaft w WHERE w.host = :host AND w.datum = :date and w.minute = 0")
    List<Integer[]> findDailyDataset(String host, LocalDate date);

    @Query("SELECT w.minute, w.value from Wirtschaft w WHERE w.host = :host AND w.datum = :date and w.hour = :hour")
    List<Integer[]> findHourlyDataset(String host, LocalDate date, int hour);
}
