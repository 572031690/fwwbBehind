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
    public Integer addDepository(Depository depository) {
        return depositoryMapper.addDepository(depository);
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
