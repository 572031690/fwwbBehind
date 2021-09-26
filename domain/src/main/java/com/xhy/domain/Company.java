package com.xhy.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Company implements Serializable {
//企业id
    private int id;
    //企业名称
    private  String matter;
    //公司坐标
    private String port;
    //省份
    private String countyname;

}
