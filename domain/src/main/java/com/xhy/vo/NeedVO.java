package com.xhy.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;


@Data
public class NeedVO implements Serializable {
    int page;
    int limit;
    String searchName;  //传递搜索参数
    String selectName;
    String department; //需求单位
    String itemtype; //物料类别
    String itemid;   //物料编号
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date needday; //需求时间
}
