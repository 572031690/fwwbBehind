package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.InRepositoryMapper;
import com.xhy.domain.InRepository;
import com.xhy.service.InRepositoryService;
import com.xhy.vo.InRepositoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InRepositoryServiceimpl implements InRepositoryService {

    @Autowired
    InRepositoryMapper inRepositoryMapper;


    @Override
    public List<InRepository> findInRepository(InRepositoryVO inRepositoryVO) {

        PageHelper.startPage(inRepositoryVO.getPage(),inRepositoryVO.getLimit());
        return inRepositoryMapper.findInRepository(inRepositoryVO);
    }

    @Override
    public Integer addInRepository(InRepository inRepository) {
        return inRepositoryMapper.addInRepository(inRepository);
    }

    @Override
    public Integer updateInRepository(InRepository inRepository) {
        return inRepositoryMapper.updateInRepository(inRepository);
    }

    @Override
    public Integer deleteInRepository(int id) {
        return inRepositoryMapper.deleteInRepository(id);
    }
}
