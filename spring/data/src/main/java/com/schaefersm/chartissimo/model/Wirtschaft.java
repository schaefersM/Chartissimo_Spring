package com.schaefersm.chartissimo.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Wirtschaft")
public class Wirtschaft extends Dataset{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "host")
    private String host;

    @Column(name = "value")
    private int value;

    @Column(name = "hour")
    private int hour;

    @Column(name = "minute")
    private int minute;

    @Column(name = "datum")
    private LocalDate datum;

    public Wirtschaft(int value, int hour) {
        this.value = value;
        this.hour = hour;
    }

    public Wirtschaft(){}

}
