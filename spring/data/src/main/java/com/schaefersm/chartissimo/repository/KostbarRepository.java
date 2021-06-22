package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Kostbar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface KostbarRepository extends CrudRepository<Kostbar, Integer> {
    @Query("SELECT k.hour, k.value from Kostbar k WHERE k.host = :host AND k.datum = :date and k.minute = 0")
    List<Integer[]> findDailyDataset(String host, LocalDate date);

    @Query("SELECT k.minute, k.value from Kostbar k WHERE k.host = :host AND k.datum = :date and k.hour = :hour")
    List<Integer[]> findHourlyDataset(String host, LocalDate date, int hour);
}
