package com.schaefersm.chartissimo.exception;

public class UserChartsNotFoundException extends RuntimeException{

    public UserChartsNotFoundException() { super("No charts for this user found!");}

}
