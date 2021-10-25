package com.xhy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Need implements Serializable {
    private int needid;
    private String needtitle;
    private String itemid;
    private String itemtype;
    private int neednum;
    private String unit;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date needday;
    private int neederid;
    private String department;
    private String comment;
    private int uptype;
    private int planName;
    private int approvaltype;
    private String taskId;

}
