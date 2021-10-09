package com.xhy.Mapper;

import com.xhy.domain.Act_Buy;
import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;

import java.util.List;

public interface ActMapper {
    /*获取审批信息*/
    List<Need> findNeedTaskList(String username);

    /*添加需求审批表的信息*/
    Integer addActNeed(Act_Need act_need);

    /*查看需求审批表的信息*/
    List<Act_Need> findActNeed(Integer businessKey);

    /*添加采购审批表的信息*/
    Integer addActBuy(Act_Buy act_buy);

    /*查看采购审批表的信息*/
    List<Act_Buy> findActBuy(Integer businessKey);
}