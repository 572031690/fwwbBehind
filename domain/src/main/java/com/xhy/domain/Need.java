package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Need implements Serializable {
    private int needid;
    private String needtitle;
    private String itemid;
    private String itemtype;
    private int neednum;
    private String unti;
    private String needday;
    private int neederid;
    private String comment;
    private int uptype;

    private String taskId;

}
