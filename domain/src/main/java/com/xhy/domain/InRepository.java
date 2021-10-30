package com.xhy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class InRepository implements Serializable {
    private int id; //入库编号
    private String name; //入库材料名称
    private String itemid; //入库材料编号
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time; //入库时间
    private int num;//入库数量
    private String unit; //入库单位
    private int inRept; //入库批次
    private int buyid; //采购计划编号
    private int status;  //完成状态值
}
