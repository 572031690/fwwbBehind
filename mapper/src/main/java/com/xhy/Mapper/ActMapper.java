package com.xhy.Mapper;

import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;

import java.util.List;

public interface ActMapper {
    /*获取审批信息*/
    List<Need> findAct_Need(String username);

    /*添加需求审批表的信息*/
    Integer addActNeed(Act_Need act_need);
}