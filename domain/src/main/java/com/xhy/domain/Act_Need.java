package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Act_Need implements Serializable {
    private  int id;
    private int businessId;
    private String starttime;
    private String endTime;
    private String auther;
    private String upname;
    private String text;
}
