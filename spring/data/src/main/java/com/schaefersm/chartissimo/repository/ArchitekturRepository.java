package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Architektur;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArchitekturRepository extends CrudRepository<Architektur, Integer> {
    List<Architektur> findAllByHostAndDatumAndHour(String host, LocalDate date, int hour);

    @Query(value = "SELECT new Architektur (a.value, a.hour) from Architektur a WHERE a.host = :host AND a.datum = :date and a.minute = 0")
    List<Architektur> findAllByHostAndDatumAndMinute(String host, LocalDate date);

}
