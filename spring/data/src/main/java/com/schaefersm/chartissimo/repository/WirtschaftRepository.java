package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Wirtschaft;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WirtschaftRepository extends CrudRepository<Wirtschaft, Integer> {

    List<Wirtschaft> findAllByHostAndDatumAndHour(String host, LocalDate date, int hour);

    @Query(value = "SELECT new Wirtschaft(w.value, w.hour) from Wirtschaft w WHERE w.host = :host AND w.datum = :date and w.minute = 0")
    List<Wirtschaft> findAllByHostAndDatumAndMinute(String host, LocalDate date);

}
