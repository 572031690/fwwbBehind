package com.xhy.service;


import com.xhy.domain.Act_Buy;
import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;

import java.util.List;

public interface ActService {


    /*查找需求个人代办任务*/
    List<Need> findNeedTaskList(Integer page, Integer limit, String username);

    /*添加需求审批表的信息*/
    Integer addActNeed(Act_Need act_need);

    /*查看需求审批表的信息*/
    List<Act_Need> findActNeed(Integer businessKey);

    /*添加采购审批表的信息*/
    Integer addActBuy(Act_Buy act_buy);

    /*查看采购审批表的信息*/
    List<Act_Buy> findActBuy(Integer businessKey);
}
