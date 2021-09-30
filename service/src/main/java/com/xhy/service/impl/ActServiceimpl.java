package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.ActMapper;
import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;
import com.xhy.service.ActService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActServiceimpl implements ActService {

    @Autowired
    ActMapper actMapper;
    @Autowired
    TaskService taskService;

    @Override
    public List<Need> findNeedTaskList(Integer page, Integer limit, String username) {
        PageHelper.startPage(page,limit);
        return actMapper.findNeedTaskList(username);
    }

    @Override
    public Integer addActNeed(Act_Need act_need) {
        return actMapper.addActNeed(act_need);
    }

    @Override
    public List<Act_Need> findActNeed(Integer businessKey) {
        return actMapper.findActNeed(businessKey);
    }


}
