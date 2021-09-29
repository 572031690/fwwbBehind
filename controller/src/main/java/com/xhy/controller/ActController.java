package com.xhy.controller;

import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;
import com.xhy.domain.User;
import com.xhy.service.ActService;
import com.xhy.service.NeedService;
import com.xhy.service.UserServise;
import com.xhy.vo.NeedVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/needAct")
public class ActController {

    @Autowired
    UserServise userServise;
    @Autowired
    NeedService needService;
    @Autowired
    ActService actService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

//    /*启动流程*/
    @GetMapping("/startNeedAct")
    public Map<String,Object> startNeedAct(Integer needid){
        Map<String,Object> actmap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        User user = userServise.findUser(username);
        actmap.put("userid",user.getUserid());
        Need actneed = needService.findByNeedid(needid);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("needAudit", needid.toString(), actmap);
        System.out.println("活动ID"+processInstance.getActivityId());
        System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());

        map.put("list",actneed);
        return map;
    }

    /*找出需求个人待办任务*/
    @GetMapping("/queryNeedActTask")
    public List<Need> queryNeedActTask(NeedVO needVO){
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        return actService.findAct_Need(needVO.getPage(),needVO.getLimit(),username);
    }

    /*完成审批节点*/
    @RequestMapping("/completeprocess")
    public Map<String,Object> completeprocess(Integer taskId,String text){
        Map<String,Object>  map  =new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        Set<String> roles = userServise.findRoleByUserName(username);
        if(roles.contains("需求经理") || roles.contains("购买经理") || roles.contains("总经理")){
            taskService.claim(String.valueOf(taskId),userServise.findUser(username).getRealname());
        }
         Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        System.out.println(task);

        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
        HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(userServise.findUser(username).getRealname()).singleResult();
        System.out.println(processDefinitionKey);

        if(processDefinitionKey.equals("need"))
        {
            if(roles.contains("需求专员")){
                Act_Need act_need = new Act_Need();
                act_need.setUpname(task.getAssignee());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(String.valueOf(instance.getStartTime()));
                act_need.setEndTime(String.valueOf(instance.getEndTime()));
                act_need.setText(text);
                actService.addActNeed(act_need);
            }

        }
        if(processDefinitionKey.equals("purchase"))
        {

        }
        return map;
    }
}
