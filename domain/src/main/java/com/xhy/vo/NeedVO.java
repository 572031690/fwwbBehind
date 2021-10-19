package com.xhy.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class NeedVO {
    int page;
    int limit;
    String searchName;  //传递搜索参数
    String selectName;
    String department;
    String itemtype;
    String itemid;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    String needday;
}
