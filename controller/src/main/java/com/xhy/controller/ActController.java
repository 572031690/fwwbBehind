package com.xhy.controller;

import com.xhy.domain.*;
import com.xhy.service.*;
import com.xhy.vo.BuyVo;
import com.xhy.vo.NeedVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/activiti")
public class ActController {

    @Autowired
    UserServise userServise;
    @Autowired
    NeedService needService;
    @Autowired
    RoleService roleService;
    @Autowired
    BuyService buyService;
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
    public Map<String, Object> startNeedActAgain(@RequestBody Need need) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> actmap = new HashMap<>();
        need.setUptype(0);
        needService.updateStatus(need);

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        List<Role> roles = roleService.findRole();
        List<Integer> assignee = new ArrayList<>();
        List<Integer> manager = new ArrayList<>();
        for (Role role : roles) {
            if (role.getRolename().equals("需求经理")) {
                List<UserRole> userRoles = userServise.findUserRole(role.getRoleId());
                for (UserRole userRole : userRoles) {
                    assignee.add(userRole.getUserId());
                }
            } else if (role.getRolename().equals("总经理")) {
                List<UserRole> userRoles = userServise.findUserRole(role.getRoleId());
                for (UserRole userRole : userRoles) {
                    manager.add(userRole.getUserId());
                }
            }
        }

        User user = userServise.findUser(username);
        actmap.put("userid", user.getUserid());
        actmap.put("assignee", StringUtils.join(assignee.toArray(), ","));
        actmap.put("manager", StringUtils.join(manager.toArray(), ","));
        Need actneed = needService.findByNeedid(need.getNeedid());
        runtimeService.startProcessInstanceByKey("needAudit", String.valueOf(need.getNeedid()), actmap);
        Task task = taskService.createTaskQuery().processDefinitionKey("needAudit").taskAssignee(String.valueOf(user.getUserid())).singleResult();
        actneed.setTaskId(task.getId());
        if (actneed.getUptype() == 0) {
            System.out.println("已修改审批状态");
        }
        map.put("code", "101");
        map.put("list", actneed);
        return map;

    }

     /*启动需求流程*/
    @ResponseBody
    @GetMapping("/startNeedAct")
    public Map<String, Object> startNeedAct(Integer needid) {

        Map<String, Object> actmap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        List<Role> roles = roleService.findRole();
        List<Integer> assignee = new ArrayList<>();
        List<Integer> manager = new ArrayList<>();
        for (Role role : roles) {
            if (role.getRolename().equals("需求经理")) {
                List<UserRole> userRoles = userServise.findUserRole(role.getRoleId());
                for (UserRole userRole : userRoles) {
                    assignee.add(userRole.getUserId());
                }
            } else if (role.getRolename().equals("总经理")) {
                List<UserRole> userRoles = userServise.findUserRole(role.getRoleId());
                for (UserRole userRole : userRoles) {
                    manager.add(userRole.getUserId());
                }
            }
        }
        User user = userServise.findUser(username);
        actmap.put("userid", user.getUserid());
        actmap.put("assignee", StringUtils.join(assignee.toArray(), ","));
        actmap.put("manager", StringUtils.join(manager.toArray(), ","));
        Need actneed = needService.findByNeedid(needid);
        runtimeService.startProcessInstanceByKey("needAudit", needid.toString(), actmap);
        Task task = taskService.createTaskQuery().processDefinitionKey("needAudit").taskAssignee(String.valueOf(user.getUserid())).singleResult();
        actneed.setTaskId(task.getId());
        map.put("list", actneed);
        map.put("code", "101");
        return map;
    }

    /*启动采购流程*/
    @ResponseBody
    @GetMapping("/startBuyAct")
    public Map<String, Object> startBuyAct(Integer buyid) {

        Map<String, Object> actmap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        List<Role> roles = roleService.findRole();
        List<Integer> assignee = new ArrayList<>();
        List<Integer> manager = new ArrayList<>();
        for (Role role : roles) {
            if (role.getRolename().equals("购买经理")) {
                List<UserRole> userRoles = userServise.findUserRole(role.getRoleId());
                for (UserRole userRole : userRoles) {
                    assignee.add(userRole.getUserId());
                }
            } else if (role.getRolename().equals("总经理")) {
                List<UserRole> userRoles = userServise.findUserRole(role.getRoleId());
                for (UserRole userRole : userRoles) {
                    manager.add(userRole.getUserId());
                }
            }
        }
        User user = userServise.findUser(username);
        actmap.put("userid", user.getUserid());
        actmap.put("assignee", StringUtils.join(assignee.toArray(), ","));
        actmap.put("manager", StringUtils.join(manager.toArray(), ","));
        Buy actbuy = buyService.findBuyById(buyid);
        runtimeService.startProcessInstanceByKey("buyAudit", buyid.toString(), actmap);
        Task task = taskService.createTaskQuery().processDefinitionKey("buyAudit").taskAssignee(String.valueOf(user.getUserid())).singleResult();
        actbuy.setTaskId(task.getId());
        map.put("list", actbuy);
        map.put("code", "101");
        return map;
    }

    /*找出需求个人待办任务*/
    @ResponseBody
    @GetMapping("/queryNeedActTask")
    public List<Need> queryNeedActTask(NeedVO needVO) {
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(String.valueOf(user.getUserid())).processDefinitionKey("needAudit").list();
        List<Need> needList = new ArrayList<>();
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = instance.getBusinessKey();
            Need need = needService.findByNeedid(Integer.parseInt(businessKey));
            need.setTaskId(task.getId());
            needList.add(need);
        }
        return needList;
    }
    /*找出购买个人代办任务*/
    @ResponseBody
    @GetMapping("/queryBuyActTask")
    public List<Buy> queryBuyActTask(BuyVo buyVo) {
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(String.valueOf(user.getUserid())).processDefinitionKey("buyAudit").list();
        List<Buy> buyList = new ArrayList<>();
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = instance.getBusinessKey();
            Buy buy = buyService.findBuyById(Integer.parseInt(businessKey));
            buy.setTaskId(task.getId());
            buyList.add(buy);
        }
        return buyList;
    }

    /*完成审批节点*/
    @RequestMapping("/completeprocess")
    @ResponseBody
    public Map<String, Object> completeprocess(Integer taskId, String text) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        Set<String> roles = userServise.findRoleByUserName(username);

        if (roles.contains("需求经理") || roles.contains("购买经理") || roles.contains("总经理")) {
            taskService.claim(String.valueOf(taskId), String.valueOf(userServise.findUser(username).getUserid()));
        }

        Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        if (processDefinitionKey.equals("needAudit")) {
            if (roles.contains("需求专员")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).singleResult();
                Act_Need act_need = new Act_Need();
                act_need.setUpname(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(1);
                Integer actNeed = actService.addActNeed(act_need);
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                need.setUptype(1);
                Integer upneed = needService.updateStatus(need);
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "提交");
                }
            } else if (roles.contains("需求经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).singleResult();
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                Act_Need act_need = new Act_Need();
                act_need.setUpname(userServise.findbyid(need.getNeederid()).getRealname());
                act_need.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(2);
                Integer actNeed = actService.addActNeed(act_need);
//                List<Act_Need> actNeedList = actService.findActNeed(Integer.parseInt(processInstance.getBusinessKey()));
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "经理审批同意");
//                    map.put("list", actNeedList);
                }
                need.setUptype(2);
                Integer upneed = needService.updateStatus(need);
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }

            } else if (roles.contains("总经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).singleResult();
                HashMap<Object, Object> map1 = new HashMap<>();
                map1.put("id", processInstance.getBusinessKey());
                map1.put("needStatus", "审批完成");
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                Act_Need act_need = new Act_Need();
                act_need.setUpname(userServise.findbyid(need.getNeederid()).getRealname());
                act_need.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(3);
                need.setUptype(3);
                Integer upneed = needService.updateStatus(need);
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }
                Integer actNeed = actService.addActNeed(act_need);
//                List<Act_Need> actNeedList = actService.findActNeed(Integer.parseInt(processInstance.getBusinessKey()));
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "总经理审批通过");
//                    map.put("list", actNeedList);
                }
                Buy buy = new Buy();
                buy.setBuytitle(need.getNeedtitle());
                buy.setItemtype(need.getItemtype());
                buy.setItemid(need.getItemid());
                buy.setNum(need.getNeednum());
                buy.setUnit(need.getUnit());
                buy.setComment(need.getComment());
                buy.setNeederid(String.valueOf(need.getNeederid()));
                buyService.addBuy(buy);
            }
        }
        else if(processDefinitionKey.equals("buyAudit")){
            if (roles.contains("购买专员")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).singleResult();
                Act_Buy act_buy = new Act_Buy();
                act_buy.setUpname(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(1);
                Integer actNeed = actService.addActBuy(act_buy);
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                buy.setUptype(1);
                Integer upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "提交");
                }
            } else if (roles.contains("购买经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).singleResult();
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                Act_Buy act_buy = new Act_Buy();
                act_buy.setUpname(userServise.findbyid(Integer.parseInt(buy.getBuyerid())).getRealname());
                act_buy.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(2);
                Integer actBuy = actService.addActBuy(act_buy);
//                List<Act_Need> actNeedList = actService.findActNeed(Integer.parseInt(processInstance.getBusinessKey()));
                if (actBuy != 0) {
                    map.put("code", "101");
                    map.put("status", "经理审批同意");
//                    map.put("list", actNeedList);
                }
                buy.setUptype(2);
                Integer upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }

            } else if (roles.contains("总经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).singleResult();
                HashMap<Object, Object> map1 = new HashMap<>();
                map1.put("id", processInstance.getBusinessKey());
                map1.put("needStatus", "审批完成");
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                Act_Buy act_buy = new Act_Buy();
                act_buy.setUpname(userServise.findbyid(Integer.parseInt(buy.getBuyerid())).getRealname());
                act_buy.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(3);
                buy.setUptype(3);
                Integer upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }
                Integer actNeed = actService.addActBuy(act_buy);
//                List<Act_Need> actNeedList = actService.findActNeed(Integer.parseInt(processInstance.getBusinessKey()));
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "总经理审批通过");
//                    map.put("list", actNeedList);
                }
            }
        }
        return map;
    }

    /*驳回审批节点*/
    @GetMapping("/deleteprocess")
    @ResponseBody
    public Map<String, Object> deleteprocess(Integer taskId, String text) {
        Map<String, Object> map = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());

        Set<String> roles = userServise.findRoleByUserName(username);
        if (roles.contains("需求经理") || roles.contains("购买经理") || roles.contains("总经理")) {
            taskService.claim(String.valueOf(taskId), userServise.findUser(username).getRealname());
        }
        Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();

        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
        HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(userServise.findUser(username).getRealname()).singleResult();

        Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
        Act_Need act_need = new Act_Need();
        act_need.setUpname(userServise.findbyid(need.getNeederid()).getRealname());
        act_need.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
        act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
        act_need.setStarttime(instance.getStartTime());
        act_need.setEndTime(instance.getEndTime());
        act_need.setText(text);
        act_need.setId(4);
        need.setUptype(4);

        Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
        Act_Buy act_buy = new Act_Buy();
        act_buy.setUpname(userServise.findbyid(Integer.parseInt(buy.getBuyerid())).getRealname());
        act_buy.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
        act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
        act_buy.setStarttime(instance.getStartTime());
        act_buy.setEndTime(instance.getEndTime());
        act_buy.setText(text);
        act_buy.setId(4);
        buy.setUptype(4);
        if (task.getProcessDefinitionId().contains("needAudit")) {
            actService.addActNeed(act_need);
            needService.updateStatus(need);
            map.put("code", "101");
            map.put("status", "审批驳回");
        }else if(task.getProcessDefinitionId().contains("buyAudit")){
            actService.addActBuy(act_buy);
            buyService.updateStatus(buy);
            map.put("code", "101");
            map.put("status", "审批驳回");
        }
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId, "processInstance delete");
        return map;
    }


    /**
     * 查看需求历史审批
     **/

    @GetMapping("/findHistoty")
    @ResponseBody
    public Map<String, Object> findHistoty(int needid) {
        Map<String,Object> map =  new HashMap<>();
        List<Act_Need> actNeedList = actService.findActNeed(needid);
        map.put("list",actNeedList);
        return map;
    }


    /**
     *查看购买历史审批
     * */
    @GetMapping("/findHistotyBuy")
    @ResponseBody
    public Map<String, Object> findHistotyBuy(int buyid) {
        Map<String,Object> map =  new HashMap<>();
        List<Act_Buy> actBuy = actService.findActBuy(buyid);
        map.put("list",actBuy);
        return map;
    }

}




