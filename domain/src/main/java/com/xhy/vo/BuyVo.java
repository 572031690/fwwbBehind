package com.xhy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BuyVo implements Serializable {
    int page;
    int limit;
    String searchName;
    String selectName;
    String department;
    int buyerid;
    String itemtype;
    String itemid;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date btime;
    int sortType; //空或者不传后端接收的时候默认是 0=ordertype ; 1=importancetype ; 2=arrivaltimetype ; 3=btimetype
}