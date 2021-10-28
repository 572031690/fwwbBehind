package com.xhy.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//@HeadRowHeight(value = 35) // 表头行高
//@ContentRowHeight(value = 25) // 内容行高
//@ColumnWidth(value = 50)
@Data
public class Need implements Serializable {
    @ColumnWidth(50)
    @ExcelProperty(value = "编号")
    private int needid;
    @ColumnWidth(50)
    @ExcelProperty(value = "需求计划标题")
    private String needtitle;
    @ColumnWidth(50)
    @ExcelProperty(value = "物料编号")
    private String itemid;
    @ColumnWidth(50)
    @ExcelProperty(value = "物料类别")
    private String itemtype;
    @ColumnWidth(50)
    @ExcelProperty(value = "需求数量")
    private int neednum;
    @ColumnWidth(50)
    @ExcelProperty(value = "单位")
    private String unit;
    @ColumnWidth(100)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "需求时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date needday;
    @ColumnWidth(50)
    @ExcelProperty(value = "需求人编号")
    private int neederid;
    @ColumnWidth(50)
    @ExcelProperty(value = "需求单位")
    private String department;
    @ColumnWidth(50)
    @ExcelProperty(value = "详情备注")
    private String comment;
    @ColumnWidth(50)
    @ExcelProperty(value = "审批情况")
    private int uptype;
    @ColumnWidth(50)
    @ExcelProperty(value = "供应方案")
    private int planName;
    @ColumnWidth(50)
    @ExcelProperty(value = "供应状况")
    private int approvaltype;
    @ExcelIgnore
    private String taskId;

}
