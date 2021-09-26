package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;


@Data
public class Monthsalesup implements Serializable {
    private int id;
    private String materiakName;
    private double January;
    private double February;
    private double March;
    private double April;
    private double May;
    private double June;
    private double July;
    private double August;
    private double September;
    private double October;
    private double November;
    private double December;
}
