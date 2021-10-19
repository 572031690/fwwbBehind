package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Item implements Serializable {

    private int itemid;
    private String itemtype;
    private String comment;
    private int parentid;
}
