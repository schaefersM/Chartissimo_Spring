package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Informatik;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InformatikRepository extends CrudRepository<Informatik, Integer> {
    @Query("SELECT i.hour, i.value from Informatik i WHERE i.host = :host AND i.datum = :date and i.minute = 0")
    List<Integer[]> findDailyDataset(String host, LocalDate date);

    @Query("SELECT i.minute, i.value from Informatik i WHERE i.host = :host AND i.datum = :date and i.hour = :hour")
    List<Integer[]> findHourlyDataset(String host, LocalDate date, int hour);
}
