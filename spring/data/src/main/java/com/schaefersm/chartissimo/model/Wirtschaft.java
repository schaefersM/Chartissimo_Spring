package com.schaefersm.chartissimo.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Data
public class Wirtschaft extends Dataset{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String host;

    private int value;

    private int hour;

    private int minute;

    private LocalDate datum;

    public Wirtschaft(int value, int hour) {
        this.value = value;
        this.hour = hour;
    }

    public Wirtschaft(){}

}
