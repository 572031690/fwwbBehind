package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.xhy.Mapper.NeedMapper;
import com.xhy.Mapper.UserMapper;
import com.xhy.domain.Need;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NeedServiceimpl implements NeedService {

    @Autowired
    NeedMapper needMapper;

    @Autowired
    UserMapper userMapper;

//    @Autowired
//    RuntimeService runtimeService;
//
//    @Autowired
//    TaskService taskService;
//
//    @Autowired
//    HistoryService historyService;
//
//    @Autowired
//    RepositoryService repositoryService;

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
    public List<Need> findByNeedid(int needid) {
        return needMapper.findbyid(needid);
    }

//    @Override
//    public void upNeed(Need need) {
//        Map<String ,Object> map = new HashMap<>();
//        //查询处理人
//        User user  = new User();
//        List<Integer> assignee = userMapper.getAssigneeId(user);
//        map.put("userid",need.getUserid());
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("audit", String.valueOf(need.getNeedid()),map);
//        map.put("assignee", StringUtils.join(assignee.toArray(),","));
//        Task task = taskService.createTaskQuery()
//                .taskAssignee(String.valueOf(need.getUserid()))
//                .processDefinitionKey("audit")
//                .processInstanceBusinessKey(String.valueOf(need.getNeedid()))
//                .singleResult();
//        if(task != null){
//            taskService.complete(task.getId(),map);
//            System.out.println("成功");
//            updateNeed(need);
//        }
//
//    }
//
//    @Override
//    public void auditNeed(Need need) {
//        Task task = taskService.createTaskQuery()
//                .taskAssignee(String.valueOf(need.getUserid()))
//                .processDefinitionKey("audit")
//                .processInstanceBusinessKey(String.valueOf(need.getNeedid()))
//                .singleResult();
//        Map<String,Object> map = new HashMap<>();
//        int uptype = need.getUptype();
//        map.put("uptype",uptype);
//        if(uptype == 2){
//            if(task!=null) {
//                User user  = new User();
//                List<Integer> assignee = userMapper.getAssigneeId(user);
//                map.put("assignee", StringUtils.join(assignee.toArray(),","));
//                taskService.complete(task.getId(),map);
//            }
//        }else if(uptype == 3){
//
//
//        }
//    }


}
