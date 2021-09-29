package com.xhy.service;


import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;

import java.util.List;

public interface ActService {






    /*查找需求个人代办任务*/
    List<Need> findAct_Need(Integer page, Integer limit, String username);

    /*添加需求审批表的信息*/
    Integer addActNeed(Act_Need act_need);
}
