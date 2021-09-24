package com.xhy.service;

import com.xhy.domain.Buy;
import com.xhy.domain.Need;
import com.xhy.vo.BuyVo;

import java.util.List;

public interface BuyService {
    //    查看所有采购计划
    List<Buy> findAllBuy(BuyVo buyVo);

    //  添加新的采购计划
    Integer addBuy(Buy buy);

    //  修改选中的采购计划
    Integer updateBuy(Buy buy);

    //  删除采购计划
    Integer deleteBuy(int buyid);

    //  根据ID查询
    List<Buy> findBuyById(int startNum,int pageSize,int buyid);
}
