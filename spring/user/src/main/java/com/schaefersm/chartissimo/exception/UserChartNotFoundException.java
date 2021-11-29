package com.schaefersm.chartissimo.exception;

public class UserChartNotFoundException extends RuntimeException{

    public UserChartNotFoundException() { super("Chart not found in database!");}

}
