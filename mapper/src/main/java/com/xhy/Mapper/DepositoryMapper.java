package com.xhy.Mapper;

import com.xhy.domain.Depository;
import com.xhy.domain.Item;
import com.xhy.vo.DepositoryVO;
import com.xhy.vo.ItemVO;
import jdk.jfr.internal.Repository;

import java.util.List;

public interface DepositoryMapper {

    List<Depository> findAll(DepositoryVO depositoryVO);

    Integer addDepository(Depository depository);

    Integer updataDepository(Depository depository);

    Integer deleteDepository(Integer id);

}
