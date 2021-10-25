package com.xhy.service;

import com.xhy.domain.Depository;
import com.xhy.vo.DepositoryVO;

import java.util.List;

public interface DepositoryService {

    /**
     * 查看仓库信息
     * */
    List<Depository> findDepository(DepositoryVO depositoryVO);


    /**
     * 按物料查找仓库信息
     * */
    Depository findByName(String name);

    /**
     * 添加仓库信息
     * */
    Integer addDepository(com.xhy.domain.Depository depository);

    /**
     * 修改仓库信息
     * */

    Integer updataDepository(com.xhy.domain.Depository depository);

    /**
     * 删除仓库信息
     * */
    Integer deleteDepository(Integer id);



}
