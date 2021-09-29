package com.xhy.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyVo implements Serializable {

    int page;
    int limit;
    String searchName;
    String selectName;
}