package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.BuyMapper;
import com.xhy.domain.Buy;
import com.xhy.domain.Need;
import com.xhy.service.BuyService;
import com.xhy.vo.BuyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyServiceimpl implements BuyService {

    @Autowired
    BuyMapper buyMapper;

    @Override
    public List<Buy> findAllBuy(BuyVo buyVo) {
        PageHelper.startPage(buyVo.getPage(),buyVo.getLimit());
        return buyMapper.findAll(buyVo);
    }

    @Override
    public Integer addBuy(Buy buy) {
       return buyMapper.addBuy(buy);
    }

    @Override
    public Integer updateBuy(Buy buy) {
       return buyMapper.updataBuy(buy);
    }

    @Override
    public Integer deleteBuy(int buyid) {
       return buyMapper.deleteBuy(buyid);
    }

    @Override
    public List<Buy> findBuyById(int startNum, int pageSize, int buyid) {
        PageHelper.startPage(startNum,pageSize);
        return buyMapper.findbyid(buyid);
    }
}
