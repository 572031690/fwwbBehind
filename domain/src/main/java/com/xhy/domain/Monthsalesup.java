package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;


@Data
public class Monthsalesup implements Serializable {
    private int id;
    private String materiakName;
    private double jan;
    private double feb;
    private double mar;
    private double apr;
    private double may;
    private double jun;
    private double jul;
    private double aug;
    private double sep;
    private double oct;
    private double nov;
    private double dec;
}
