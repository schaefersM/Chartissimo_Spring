package com.schaefersm.chartissimo.model;

import lombok.Data;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class Informatik extends Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String host;

    private int value;

    private int hour;

    private int minute;

    private LocalDate datum;

    public Informatik(int value, int hour) {
        this.value = value;
        this.hour = hour;
    }


    public Informatik() {}
}
