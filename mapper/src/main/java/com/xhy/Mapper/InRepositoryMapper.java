package com.xhy.Mapper;

import com.xhy.domain.InRepository;
import com.xhy.vo.inRepositoryVO;

import java.util.List;

public interface InRepositoryMapper {
    /**
     * 查询入库表全部信息
     * */
    List<InRepository> findInRepository(inRepositoryVO inRepositoryVO);

    /**
     * 添加入库信息
     * */

}
