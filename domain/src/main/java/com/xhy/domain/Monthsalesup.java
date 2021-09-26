package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;


@Data
public class Monthsalesup implements Serializable {
    private int id;
    private String materiakName;
    private double Jan;
    private double Feb;
    private double Mar;
    private double Apr;
    private double May;
    private double Jun;
    private double Jul;
    private double Aug;
    private double Sep;
    private double Oct;
    private double Nov;
    private double Dec;
}
