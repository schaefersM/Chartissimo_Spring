package com.schaefersm.chartissimo;

public enum Location {
    ARCHITEKTUR,
    INFORMATIK,
    KOSTBAR,
    WIRTSCHAFT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
