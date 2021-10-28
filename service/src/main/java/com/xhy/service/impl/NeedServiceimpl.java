package com.xhy.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.xhy.Mapper.NeedMapper;
import com.xhy.Mapper.UserMapper;
import com.xhy.domain.Need;
import com.xhy.domain.Role;
import com.xhy.domain.User;
import com.xhy.service.NeedService;
import com.xhy.vo.NeedVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NeedServiceimpl implements NeedService {

    @Autowired
    NeedMapper needMapper;

    @Autowired
    UserMapper userMapper;


    @Override
    public List<Need> findAllNeed(NeedVO needVO) {
        PageHelper.startPage(needVO.getPage(),needVO.getLimit());
        return needMapper.findAll(needVO);
    }

    @Override
    public Integer addNeed(Need need) {
       return needMapper.addNeed(need);
    }

    @Override
    public Integer updateNeed(Need need) {
       return needMapper.updateNeed(need);
    }

    @Override
    public Integer deleteNeed(int needid) {
      return   needMapper.deleteNeed(needid);
    }

    @Override
    public Need findByNeedid(int needid) {
        return needMapper.findbyid(needid);
    }


    @Override
    public Integer updateStatus(Need need) {
        return needMapper.updateStatus(need);
    }

    @Override
    public List<Need> findNeed() {
        return needMapper.findNeed();
    }


}
