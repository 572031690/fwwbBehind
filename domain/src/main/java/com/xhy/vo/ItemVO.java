package com.xhy.vo;

import lombok.Data;

@Data
public class ItemVO {
    private int page;
    private int limit;
    private String searchName;  //传递搜索参数
    private int selectName;  //查询筛选状态
}
