package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Buy implements Serializable {
    private int buyid;
    private String buytitle;
    private String btime;
    private int bdaytype;
    private String itemtype;
    private String itemid;
    private String unit;
    private String buyerid;
    private String neederid;
    private int num;
    private int uptype;
    private int taskId;


}
