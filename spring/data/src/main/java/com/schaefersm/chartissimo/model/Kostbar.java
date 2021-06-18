package com.schaefersm.chartissimo.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Kostbar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String host;

    private int value;
    
    private int hour;
    
    private int minute;

    private LocalDate datum;
    
    @Override
    public String toString() {
        return "Kostbar [datum=" + datum + ", host=" + host + "]";
    }


    public Kostbar(String host, int value, int hour, int minute, LocalDate datum) {
        this.host = host;
        this.value = value;
        this.hour = hour;
        this.minute = minute;
        this.datum = datum;
    }


    public Kostbar() {
    }



    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
