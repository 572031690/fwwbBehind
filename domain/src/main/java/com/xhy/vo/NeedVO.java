package com.xhy.vo;

import lombok.Data;

@Data
public class NeedVO {
    int page;
    int limit;
    String searchName;  //传递搜索参数
    String selectName;
}
