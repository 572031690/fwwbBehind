package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Need implements Serializable {
    private int needid;
    private String needtitle;
    private String itemtype;
    private String itemid;
    private int neednum;
    private String needday;
    private int userid;
    private String comment;
//    private int needlevel;
    private int uptype;
    private int authuserid;

}
