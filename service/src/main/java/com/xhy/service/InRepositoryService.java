package com.xhy.service;

import com.xhy.domain.InRepository;
import com.xhy.vo.InRepositoryVO;

import java.util.List;

public interface InRepositoryService {

    /**
     * 获取入库表全部信息
     * */
    List<InRepository> findInRepository(InRepositoryVO inRepositoryVO);

    /**
     * 添加入库信息
     * */

    Integer addInRepository(InRepository inRepository);

    /**
     * 修改入库信息
     * */

    Integer updateInRepository(InRepository inRepository);

    /**
     * 删除入库信息
     * */

    Integer deleteInRepository(int id);
}
