package com.xhy.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyVo implements Serializable {

    int page;
    int limit;
    String searchName;
    String selectName;
    int ordertype; //降序传int 1
    int importancetype; //按照重要性排序 （int ）1 是启动
    int arrivaltimetype;//按照到货时间排序 （int ）1 是启动
    int btimetype;//按照需求时间排序 （int ）1 是启动
}