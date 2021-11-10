package com.schaefersm.chartissimo.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Kostbar extends Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String host;
    private LocalDate datum;
    private int value;
    @ToString.Exclude
    private int hour;
    @ToString.Exclude
    private int minute;

    public Kostbar(int value, int hour) {
        this.value = value;
        this.hour = hour;
    }

    public Kostbar(){}

}
