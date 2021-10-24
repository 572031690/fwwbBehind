package com.xhy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OutRepository implements Serializable {
    private int id;//出库编号
    private String name; //出库材料名称
    private String itemid; //出材料编号
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;//出库时间
    private int num;//出库数量
    private String unit;//出库单位
    private int outRept ; //出库批次
    private int needid; //需求订单编号
}
