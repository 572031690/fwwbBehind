package com.xhy.domain;

import lombok.Data;

@Data
public class Depository {
    private int id;
    private String itemcode;
    private String name;
    private String comment;
    private int stock;
    private int totalstock;
}
