package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Kostbar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface KostbarRepository extends CrudRepository<Kostbar , Integer> {

    List<Kostbar> findAllByHostAndDatumAndHour(String host, LocalDate date, int hour);

    @Query(value = "SELECT new Kostbar (k.value, k.hour) from Kostbar k WHERE k.host = :host AND k.datum = :date and k.minute = 0")
    List<Kostbar> findAllByHostAndDatumAndMinute(String host, LocalDate date);

}
