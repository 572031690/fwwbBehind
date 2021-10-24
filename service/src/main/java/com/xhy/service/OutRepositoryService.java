package com.xhy.service;

import com.xhy.domain.InRepository;
import com.xhy.domain.OutRepository;
import com.xhy.vo.InRepositoryVO;
import com.xhy.vo.OutRepositoryVO;

import java.util.List;

public interface OutRepositoryService {

    /**
     * 查询出库表全部信息
     * */
    List<OutRepository> findOutRepository(OutRepositoryVO outRepositoryVO);

    /**
     * 添加出库信息
     * */

    Integer addOutRepository(OutRepository outRepository);

    /**
     * 修改出库信息
     * */

    Integer updateOutRepository(OutRepository outRepository);

    /**
     * 删除出库信息
     * */

    Integer deleteOutRepository(int id);
}
