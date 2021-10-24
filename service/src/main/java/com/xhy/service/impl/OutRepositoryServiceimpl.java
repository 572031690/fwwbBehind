package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.OutRepositoryMapper;
import com.xhy.domain.OutRepository;
import com.xhy.service.OutRepositoryService;
import com.xhy.vo.OutRepositoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutRepositoryServiceimpl implements OutRepositoryService {

    @Autowired
    OutRepositoryMapper outRepositoryMapper;

    @Override
    public List<OutRepository> findOutRepository(OutRepositoryVO outRepositoryVO) {
        PageHelper.startPage(outRepositoryVO.getPage(),outRepositoryVO.getLimit());
        return outRepositoryMapper.findOutRepository(outRepositoryVO);
    }

    @Override
    public Integer addOutRepository(OutRepository outRepository) {
        return outRepositoryMapper.addOutRepository(outRepository);
    }

    @Override
    public Integer updateOutRepository(OutRepository outRepository) {
        return outRepositoryMapper.updateOutRepository(outRepository);
    }

    @Override
    public Integer deleteOutRepository(int id) {
        return outRepositoryMapper.deleteOutRepository(id);
    }
}
