package com.schaefersm.chartissimo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Architektur {

//    public Architektur() {
//        super();
//    }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String host;

   private int value;

   private int hour;

   private int minute;

   private LocalDate datum;

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

   public LocalDate getDate() {
       return datum;
   }

   public void setDate(LocalDate datum) {
       this.datum = datum;
   }

}
