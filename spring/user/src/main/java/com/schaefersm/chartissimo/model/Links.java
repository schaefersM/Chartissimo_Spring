package com.schaefersm.chartissimo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Links {

    String self;
    String next;
    String prev;
    String first;
    String last;

}
