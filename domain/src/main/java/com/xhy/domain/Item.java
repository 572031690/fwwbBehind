package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Item implements Serializable {

    private String itemid;
    private String itemtype;
    private String comment;
    private int stock;
    private String unit;
}
