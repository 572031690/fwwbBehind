package com.xhy.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private int id;
    private String name;
    private String type;
    private String url;
    private String permission;
    private int isDisabled;
    private int isDisplayed;
}
