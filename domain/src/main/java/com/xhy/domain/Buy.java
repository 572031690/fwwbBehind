package com.xhy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Buy implements Serializable {
    private int buyid;
    private String buytitle;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date btime;
    private String itemtype;
    private String itemid;
    private String unit;
    private int buyerid;
    private int neederid;
    private int auditid;
    private int num;
    private int uptype;
    private String comment;
    private String taskId;


}
