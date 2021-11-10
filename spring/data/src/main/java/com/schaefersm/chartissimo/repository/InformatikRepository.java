package com.schaefersm.chartissimo.repository;

import java.time.LocalDate;
import java.util.List;

import com.schaefersm.chartissimo.model.Informatik;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InformatikRepository extends CrudRepository<Informatik, Integer> {
    List<Informatik> findAllByHostAndDatumAndHour(String host, LocalDate date, int hour);

    @Query(value = "SELECT new Informatik (i.value, i.hour) from Informatik i WHERE i.host = :host AND i.datum = :date and i.minute = 0")
    List<Informatik> findAllByHostAndDatumAndMinute(String host, LocalDate date);

}
