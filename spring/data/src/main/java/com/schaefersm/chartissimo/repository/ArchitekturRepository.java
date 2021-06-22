package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Architektur;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArchitekturRepository extends CrudRepository<Architektur, Integer> {
    @Query("SELECT a.hour, a.value from Architektur a WHERE a.host = :host AND a.datum = :date and a.minute = 0")
    List<Integer[]> findDailyDataset(String host, LocalDate date);

    @Query("SELECT a.minute, a.value from Architektur a WHERE a.host = :host AND a.datum = :date and a.hour = :hour")
    List<Integer[]> findHourlyDataset(String host, LocalDate date, int hour);
}
