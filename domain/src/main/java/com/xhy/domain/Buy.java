package com.xhy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Buy implements Serializable {
    private int buyid;
    private String buytitle;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date btime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date arrivaltime;
    private String itemtype;
    private String itemid;
    private String unit;
    private int buyerid;
    private int neederid;
    private int num;
    private int uptype;
    private String comment;
    private String department;
    private int importance;
    private int buytype;
    private int needid;
    private String taskId;


}
