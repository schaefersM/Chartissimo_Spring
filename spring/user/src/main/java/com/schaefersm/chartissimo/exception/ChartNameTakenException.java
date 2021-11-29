package com.schaefersm.chartissimo.exception;

public class ChartNameTakenException extends RuntimeException {

    public ChartNameTakenException() {
        super("Chart name already taken!");
    }

}
