package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.DepositoryMapper;
import com.xhy.domain.Depository;
import com.xhy.service.DepositoryService;
import com.xhy.vo.DepositoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositoryServiceimpl implements DepositoryService {

    @Autowired
    DepositoryMapper depositoryMapper;

    @Override
    public List<Depository> findDepository(DepositoryVO depositoryVO) {
        PageHelper.startPage(depositoryVO.getPage(),depositoryVO.getLimit());
        return depositoryMapper.findAll(depositoryVO);
    }

    @Override
    public Depository findByName(String name) {
        return depositoryMapper.findByName(name);
    }

    @Override
    public Integer addDepository(Depository depository) {
        int new_stock = 0;
        Depository byName = depositoryMapper.findByName(depository.getName());
        if(!byName.equals(null)){
           new_stock =  depository.getStock() +  byName.getStock(); // 添加库存的量
           if(new_stock<=byName.getTotalstock() && depository.getStock() <=byName.getTotalstock()){
               depository.setStock(new_stock);//更新的库存
               depository.setId(byName.getId());//存在的物料的id
               depository.setTotalstock(byName.getTotalstock());
               return depositoryMapper.updataDepository(depository);
           }else
           {
               return 0;
           }
        }else return depositoryMapper.addDepository(depository);
    }

    @Override
    public Integer updataDepository(Depository depository) {
        return depositoryMapper.updataDepository(depository);
    }

    @Override
    public Integer deleteDepository(Integer id) {
        return depositoryMapper.deleteDepository(id);
    }
}
