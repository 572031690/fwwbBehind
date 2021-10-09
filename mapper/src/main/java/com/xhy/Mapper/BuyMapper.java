package com.xhy.Mapper;

import com.xhy.domain.Buy;
import com.xhy.domain.Need;
import com.xhy.vo.BuyVo;

import java.util.List;

public interface BuyMapper {

    List<Buy> findAll(BuyVo buyVo);

    Buy findbyid(int buyid);

    Integer addBuy(Buy buy);

    Integer updataBuy(Buy buy);

    Integer deleteBuy(int buyid);

    Integer updateStatus(Buy buy);

    List<Buy> findBuy();

}
