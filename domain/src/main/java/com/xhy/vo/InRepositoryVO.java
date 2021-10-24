package com.xhy.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class InRepositoryVO implements Serializable {
    private int page; //页数
    private int limit;//显示条数
    private String searchName; //物料名称
    private String selectName;//采购订单编号
}
