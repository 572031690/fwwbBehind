package com.xhy.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Buy implements Serializable {

    @ExcelProperty(value = "编号")
    private int buyid;
    @ExcelProperty(value = "采购计划标题")
    private String buytitle;
    @ExcelProperty(value = "需求时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date btime;
    @ExcelProperty(value = "到货时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date arrivaltime;
    @ExcelProperty(value = "物料类别")
    private String itemtype;
    @ExcelProperty(value = "物料编码")
    private String itemid;
    @ExcelProperty(value = "采购申请人编号")
    private int buyerid;
    @ExcelProperty(value = "需求申请人编号")
    private int neederid;
    @ExcelProperty(value = "数量")
    private int num;
    @ExcelProperty(value = "单位")
    private String unit;
    @ExcelProperty(value = "审批状态")
    private int uptype;
    @ExcelProperty(value = "详情备注")
    private String comment;
    @ExcelProperty(value = "需求单位")
    private String department;
    @ExcelProperty(value = "重要性")
    private int importance;
    @ExcelProperty(value = "购买状态")
    private int buytype;
    @ExcelIgnore
    private int needid;
    @ExcelIgnore
    private String taskId;


}
