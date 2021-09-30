package com.xhy.controller;

import com.xhy.domain.Act_Need;
import com.xhy.domain.Need;
import com.xhy.domain.User;
import com.xhy.service.ActService;
import com.xhy.service.NeedService;
import com.xhy.service.UserServise;
import com.xhy.vo.NeedVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/activiti")
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


    /*修改并重启审批流程*/
    @PostMapping("/startNeedActAgain")
    public Map<String,Object> startNeedActAgain(@RequestBody Need need){
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> actmap = new HashMap<>();
        need.setUptype(0);
        needService.updateNeed(need);
        needService.updateStatus(need);

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        actmap.put("userid", user.getUserid());
        Need actneed = needService.findByNeedid(need.getNeedid());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("needAudit", String.valueOf(need.getNeedid()), actmap);
        System.out.println("活动ID" + processInstance.getActivityId());
        System.out.println("流程定义ID" + processInstance.getProcessDefinitionId());
        if(actneed.getUptype()==0){
            System.out.println("已修改审批状态");
        }
        map.put("code","101");
        map.put("list", actneed);
        return map;

    }

    //    /*启动流程*/
    @GetMapping("/startNeedAct")
    public Map<String, Object> startNeedAct(Integer needid) {
        Map<String, Object> actmap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        System.out.println(username);

        User user = userServise.findUser(username);
        System.out.println(user);

        actmap.put("userid", user.getUserid());

        Need actneed = needService.findByNeedid(needid);

        System.out.println(actneed);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("needAudit", needid.toString(), actmap);
        System.out.println("流程定义的key"+processInstance.getProcessDefinitionKey());
        System.out.println("流程定义ID" + processInstance.getProcessDefinitionId());
        System.out.println("流程定义的name"+processInstance.getName());

        map.put("list", actneed);
        return map;
    }

    /*找出需求个人待办任务*/
    @GetMapping("/queryNeedActTask")
    public List<Need> queryNeedActTask(NeedVO needVO) {
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        return actService.findAct_Need(needVO.getPage(), needVO.getLimit(), username);
    }

    /*完成审批节点*/
    @RequestMapping("/completeprocess")
    public Map<String, Object> completeprocess(Integer taskId,  String text) {
        Map<String, Object> map = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        Set<String> roles = userServise.findRoleByUserName(username);
        if (roles.contains("需求经理") || roles.contains("购买经理") || roles.contains("总经理")) {
            taskService.claim(String.valueOf(taskId), userServise.findUser(username).getRealname());
        }
        Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        System.out.println(task);

        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
        HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(userServise.findUser(username).getRealname()).singleResult();
        System.out.println(processDefinitionKey);

        if (processDefinitionKey.equals("need")) {
            if (roles.contains("需求专员")) {
                taskService.complete(String.valueOf(taskId));
                Act_Need act_need = new Act_Need();
                act_need.setUpname(task.getAssignee());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(String.valueOf(instance.getStartTime()));
                act_need.setEndTime(String.valueOf(instance.getEndTime()));
                act_need.setText(text);
                act_need.setId(1);
                Integer actNeed = actService.addActNeed(act_need);
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                need.setUptype(1);
                needService.updateStatus(need);
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "提交");
                }
            } else {
                    taskService.complete(String.valueOf(taskId));
                    Need need1 = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                    Act_Need act_need = new Act_Need();
                    act_need.setUpname(String.valueOf(need1.getNeederid()));
                    act_need.setAuther(task.getAssignee());
                    act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                    act_need.setStarttime(String.valueOf(instance.getStartTime()));
                    act_need.setEndTime(String.valueOf(instance.getEndTime()));
                    act_need.setText(text);
                    act_need.setId(2);
                    Integer actNeed = actService.addActNeed(act_need);
                    List<Act_Need> actNeedList = actService.findActNeed(Integer.parseInt(processInstance.getBusinessKey()));
                    if (actNeed != 0) {
                        map.put("code", "101");
                        map.put("status", "经理审批同意");
                        map.put("list",actNeedList);
                    }
            }
            if (roles.contains("总经理")) {
                taskService.complete(String.valueOf(taskId));
                HashMap<Object, Object> map1 = new HashMap<>();
                map1.put("id", processInstance.getBusinessKey());
                map1.put("needStatus", "审批完成");
                Need need1 = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                Act_Need act_need = new Act_Need();
                act_need.setUpname(String.valueOf(need1.getNeederid()));
                act_need.setAuther(task.getAssignee());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(String.valueOf(instance.getStartTime()));
                act_need.setEndTime(String.valueOf(instance.getEndTime()));
                act_need.setText(text);
                act_need.setId(2);
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                need.setUptype(2);
                needService.updateStatus(need);
                Integer actNeed = actService.addActNeed(act_need);
                List<Act_Need> actNeedList = actService.findActNeed(Integer.parseInt(processInstance.getBusinessKey()));
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "总经理审批通过");
                    map.put("list",actNeedList);

                }
            }
        }if (processDefinitionKey.equals("purchase")) {

        }
        return map;
    }

    /*驳回审批节点*/
    @GetMapping("/deleteprocess")
    public Map<String,Object> deleteprocess(Integer taskId,String text){
        Map<String, Object> map = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        Set<String> roles = userServise.findRoleByUserName(username);
        if (roles.contains("需求经理") || roles.contains("购买经理") || roles.contains("总经理")) {
            taskService.claim(String.valueOf(taskId), userServise.findUser(username).getRealname());
        }
        Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        System.out.println(task);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String  s = processInstance.getBusinessKey();
        System.out.println(s);

        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
        HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(userServise.findUser(username).getRealname()).singleResult();

        Need need1 = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
        Act_Need act_need = new Act_Need();
        act_need.setUpname(String.valueOf(need1.getNeederid()));
        act_need.setAuther(task.getAssignee());
        act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
        act_need.setStarttime(String.valueOf(instance.getStartTime()));
        act_need.setEndTime(String.valueOf(instance.getEndTime()));
        act_need.setText(text);
        act_need.setId(3);
        Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
        need.setUptype(3);
        System.out.println( task.getProcessDefinitionId().contains("needAudit"));
        if (task.getProcessDefinitionId().contains("needAudit")) {
            actService.addActNeed(act_need);
            needService.updateStatus(need);
            map.put("code","101");
            map.put("status","审批驳回");
        }
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId,"processInstance delete");
        return map;
    }
}



