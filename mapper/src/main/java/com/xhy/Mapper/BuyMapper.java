package com.xhy.Mapper;

import com.xhy.domain.Buy;
import com.xhy.vo.BuyVo;

import java.util.List;

public interface BuyMapper {

    List<Buy> findAll(BuyVo buyVo);

    List<Buy> findbyid(int buyid);

    Integer addBuy(Buy buy);

    Integer updataBuy(Buy buy);

    Integer deleteBuy(int buyid);

}
