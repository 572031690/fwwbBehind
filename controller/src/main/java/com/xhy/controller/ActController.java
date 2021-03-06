package com.xhy.controller;


import com.xhy.domain.*;
import com.xhy.service.*;
import com.xhy.vo.BuyVo;
import com.xhy.vo.NeedVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    DepositoryService depositoryService;


    /*修改并重启审批流程*/
    @RequiresPermissions("needer:startAgain")
    @ResponseBody
    @GetMapping("/startNeedActAgain")
    public Map<String, Object> startNeedActAgain(Integer needid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> actmap = new HashMap<>();
        Need need = needService.findByNeedid(needid);
        need.setUptype(0);
        Integer upStatus = needService.updateStatus(need);
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
        runtimeService.startProcessInstanceByKey("needAudit", String.valueOf(need.getNeedid()), actmap);
        Task task = taskService.createTaskQuery().processDefinitionKey("needAudit").taskAssignee(String.valueOf(user.getUserid())).singleResult();
        actneed.setTaskId(task.getId());
        if (upStatus != 0) {
            map.put("status", "已修改审批状态");
        }
        map.put("code", "101");
        map.put("list", actneed);
        return map;
    }

    /*修改并重启采购流程*/
    @RequiresPermissions("buyer:startAgain")
    @ResponseBody
    @GetMapping("/startBuyActAgain")
    public Map<String, Object> startBuyActAgain(Integer buyid) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> actmap = new HashMap<>();
        Buy buy = buyService.findBuyById(buyid);
        buy.setBuyid(buyid);
        buy.setUptype(0);
        Integer status = buyService.updateStatus(buy);
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        List<Role> roles = roleService.findRole();
        List<Integer> assignee = new ArrayList<>();
        List<Integer> manager = new ArrayList<>();
        for (Role role : roles) {
            if (role.getRolename().equals("采购经理")) {
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
        runtimeService.startProcessInstanceByKey("buyAudit", String.valueOf(buy.getBuyid()), actmap);
        Task task = taskService.createTaskQuery().processDefinitionKey("buyAudit").taskAssignee(String.valueOf(user.getUserid())).singleResult();
        actbuy.setTaskId(task.getId());
        if (status != 0) {
            map.put("status", "已修改审批状态");
        }
        map.put("code", "101");
        map.put("list", actbuy);
        return map;
    }

    /*启动需求流程*/
    @RequiresPermissions("needer:startNeed")
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
        List<Need> needList = new ArrayList<>();
        runtimeService.startProcessInstanceByKey("needAudit", needid.toString(), actmap);
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("needAudit").taskAssignee(String.valueOf(user.getUserid())).list();
        for (Task task : taskList) {
            Need actneed = needService.findByNeedid(needid);
            actneed.setTaskId(task.getId());
            needList.add(actneed);
        }
        map.put("list", needList);
        map.put("code", "101");
        return map;
    }

    /*启动采购流程*/
    @RequiresPermissions("buyer:startBuy")
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
            if (role.getRolename().equals("采购经理")) {
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
        List<Buy> buyList = new ArrayList<>();
        runtimeService.startProcessInstanceByKey("buyAudit", buyid.toString(), actmap);
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("buyAudit").taskAssignee(String.valueOf(user.getUserid())).list();
        for (Task task : taskList) {
            Buy actbuy = buyService.findBuyById(buyid);
            actbuy.setTaskId(task.getId());
            buyList.add(actbuy);
        }
        map.put("list", buyList);
        map.put("code", "101");
        return map;
    }

    /*找出需求个人待办任务*/
    @RequiresPermissions("needManger:listUpNeed")
    @ResponseBody
    @PostMapping("/queryNeedActTask")
    public Map<String, Object> queryNeedActTask(@RequestBody NeedVO needVO) {
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        Map<String, Object> map = new HashMap<>();
        User user = userServise.findUser(username);
        int count = 0;
        int flag = 0;
        int index = 1;
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(String.valueOf(user.getUserid())).processDefinitionKey("needAudit").list();
        List<Need> needList = new ArrayList<>();
        List<Need> finalNeed = new ArrayList<>();
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = instance.getBusinessKey();
            Need need = needService.findByNeedid(Integer.parseInt(businessKey));
            need.setTaskId(task.getId());
            Date startDay = need.getNeedday();
            Date endDay = new Date();
            long start = startDay.getTime();
            long end = endDay.getTime();
            long berween_days = (end - start) / (1000 * 3600 * 24);
            System.out.println("berween_days"+berween_days);
            map.put("dueTime", berween_days); //剩余时间
            if (berween_days >= 2) {
                need.setUptype(5); //审批逾期
                needService.updateStatus(need);
                List<UserRole> userRole_manager = userServise.findUserRole(10001);
                System.out.println("task:"+task.getId());   //查看当前的任务id
                taskService.claim(task.getId(), String.valueOf(user.getUserid()));
                taskService.complete(task.getId(),map);
                HistoricActivityInstance instance1 = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityId("_5").singleResult();
                if (userRole_manager != null |instance1==null) {
                    User manager = userServise.findbyid(userRole_manager.get(0).getUserId());
                    Task task2 = taskService.createTaskQuery().taskCandidateUser(String.valueOf(manager.getUserid())).processInstanceId(processInstanceId).taskDefinitionKey("_4").singleResult();
                    System.out.println("task2:" + task2);//控制台查看任务的id
                    taskService.claim(task2.getId(), String.valueOf(userRole_manager.get(0).getUserId()));
                    taskService.complete(task2.getId());
                    HistoricActivityInstance instance2 = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityId("_5").singleResult();
                    if (instance2 != null) {
                        map.put("code", "102");
                        map.put("status", need.getNeedid() + "号," + need.getNeedtitle() + "审批已逾期");
                    }
                }
            }
        needList.add(need);
        count++;
    }
        if(needVO.getSearchName().equals(null) |needVO.getSearchName().isEmpty())
    {
        for (Need need : needList) {
            if (index >= needVO.getPage()) {
                if (flag <= needVO.getLimit()) {
                    finalNeed.add(need);
                    flag++;
                } else {
                    break;
                }
            }
            index++;
        }
    } else

    {
        for (Need need : needList) {
            if (need.getNeedtitle().equals(needVO.getSearchName())) {
                finalNeed.add(need);
            }
        }
    }
        map.put("count",count);
        map.put("list",finalNeed);
        return map;
}


    /*找出购买个人代办任务*/
    @RequiresPermissions("buyManger:aduitBuy")
    @ResponseBody
    @PostMapping("/queryBuyActTask")
    public Map<String, Object> queryBuyActTask(@RequestBody BuyVo buyVo) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        int count = 0;
        int flag = 0;
        int index = 1;
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(String.valueOf(user.getUserid())).processDefinitionKey("buyAudit").list();
        List<Buy> buyList = new ArrayList<>();
        List<Buy> finalBuy = new ArrayList<>();
        System.out.println(tasks);
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = instance.getBusinessKey();
            Buy buy = buyService.findBuyById(Integer.parseInt(businessKey));
            buy.setTaskId(task.getId());
            /*查看是否逾期*/
            Date startDay = buy.getBtime();
            Date endDay = new Date();
            long start = startDay.getTime();
            long end = endDay.getTime();
            long between_days = (start - end) / (1000 * 3600 * 24);
            map.put("dueTime", between_days);
            if (between_days >= 2) {
                buy.setUptype(5);
                buyService.updateBuy(buy);
                List<UserRole> userRole_manager = userServise.findUserRole(10001);
                System.out.println("task:" + task.getId());   //查看当前的任务id
                taskService.claim(task.getId(), String.valueOf(user.getUserid()));
                taskService.complete(task.getId(), map);
                HistoricActivityInstance instance1 = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityId("_5").singleResult();
                if (userRole_manager != null | instance1 == null) {
                    User manager = userServise.findbyid(userRole_manager.get(0).getUserId());
                    Task task2 = taskService.createTaskQuery().taskCandidateUser(String.valueOf(manager.getUserid())).processInstanceId(processInstanceId).taskDefinitionKey("_4").singleResult();
                    System.out.println("task2:" + task2);//控制台查看任务的id
                    taskService.claim(task2.getId(), String.valueOf(userRole_manager.get(0).getUserId()));
                    taskService.complete(task2.getId());
                    HistoricActivityInstance instance2 = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityId("_5").singleResult();
                    if (instance2 != null) {
                        map.put("code", "102");
                        map.put("status", buy.getBuyid() + "号," + buy.getBuytitle() + "审批已逾期");
                    }
                }
            }
                buyList.add(buy);
                count++;
            }
        if (buyVo.getSearchName().equals(null) | buyVo.getSearchName().isEmpty()) {
            for (Buy buy : buyList) {
                if (index >= buyVo.getPage()) {
                    if (flag <= buyVo.getLimit()) {
                        finalBuy.add(buy);
                        flag++;
                    } else {
                        break;
                    }
                }
                index++;
            }
        } else {
            for (Buy buy : buyList) {
                if (buy.getBuytitle().equals(buyVo.getSearchName())) {
                    finalBuy.add(buy);
                }
            }
        }
        System.out.println(finalBuy);
        map.put("count", count);
        map.put("list", finalBuy);
        return map;
    }

    /*完成审批节点*/
    @RequiresPermissions("Manger:activitiComplete")
    @RequestMapping("/completeprocess")
    @ResponseBody
    public Map<String, Object> completeprocess(Integer taskId, String text) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        Set<String> roles = userServise.findRoleByUserName(username);
        if (roles.contains("需求经理") | roles.contains("采购经理") | roles.contains("总经理")) {
            taskService.claim(String.valueOf(taskId), String.valueOf(userServise.findUser(username).getUserid()));
        }

        Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        if (processDefinitionKey.equals("needAudit")) {
            Act_Need act_need = new Act_Need();
            Integer actNeed = 0;
            Integer upneed = 0;
            if (roles.contains("需求专员")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_2").singleResult();
                act_need.setUpname(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(1);
                actNeed = actService.addActNeed(act_need);
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                need.setUptype(1);
                upneed = needService.updateStatus(need);
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
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_3").singleResult();
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setUpname(userServise.findbyid(need.getNeederid()).getRealname());
                act_need.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(2);
                actNeed = actService.addActNeed(act_need);
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "经理审批同意");
                }
                need.setUptype(2);
                upneed = needService.updateStatus(need);
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }
            } else if (roles.contains("总经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_4").singleResult();
                HashMap<Object, Object> map1 = new HashMap<>();
                map1.put("id", processInstance.getBusinessKey());
                map1.put("needStatus", "审批完成");
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setUpname(userServise.findbyid(need.getNeederid()).getRealname());
                act_need.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(3);
                need.setUptype(3);
                upneed = needService.updateStatus(need);
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }
                actNeed = actService.addActNeed(act_need);
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "总经理审批通过");
                }
                Depository depository = depositoryService.findByName(need.getItemtype());
                /*库存供应*/
                if (need.getNeednum() <= depository.getStock()) {  //需求数量小于等于仓库库存
                    need.setPlanName(2); //
                    need.setApprovaltype(1);
                    needService.updateNeed(need);
                } else if(need.getNeednum() >= depository.getStock() &  depository.getStock() +need.getNeednum() <=depository.getStock()){ //需求数量大于仓库库存
                    /*采购供应*/
                    need.setPlanName(1);
                    need.setApprovaltype(1);
                    needService.updateNeed(need);
                    Buy buy = new Buy();
                    buy.setBuytitle(need.getNeedtitle());
                    buy.setItemtype(need.getItemtype());
                    buy.setBtime(new Date());
                    buy.setItemid(need.getItemid());
                    buy.setNum(need.getNeednum());
                    buy.setUnit(need.getUnit());
                    buy.setComment(need.getComment());
                    buy.setNeederid(need.getNeederid());
                    buy.setNeedid(need.getNeedid());
                    buy.setBuyerid(0);
                    buy.setImportance(1);
                    buy.setDepartment(need.getDepartment());
                    buyService.addBuy(buy);
                }
                else if(need.getNeednum() >= depository.getStock() &  depository.getStock() +need.getNeednum() >=depository.getStock()){
                    map.put("tip","要采购的数量与库存数量超过了总库存,已修改");
                    /*采购供应*/
                    int latest_needNum = need.getNeednum() - depository.getStock();
                    need.setPlanName(1);
                    need.setApprovaltype(1);
                    needService.updateNeed(need);
                    Buy buy = new Buy();
                    buy.setBuytitle(need.getNeedtitle());
                    buy.setItemtype(need.getItemtype());
                    buy.setBtime(new Date());
                    buy.setItemid(need.getItemid());
                    buy.setNum(latest_needNum);
                    buy.setUnit(need.getUnit());
                    buy.setComment(need.getComment());
                    buy.setNeederid(need.getNeederid());
                    buy.setNeedid(need.getNeedid());
                    buy.setBuyerid(0);
                    buy.setImportance(1);
                    buy.setDepartment(need.getDepartment());
                    buyService.addBuy(buy);
                }
            }
        } else if (processDefinitionKey.equals("buyAudit")) {
            Act_Buy act_buy = new Act_Buy();
            Integer actBuy = 0;
            Integer upbuy = 0;
            if (roles.contains("采购专员")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_2").singleResult();
                act_buy.setUpname(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(1);
                actBuy = actService.addActBuy(act_buy);
                Buy buy = new Buy();
                buy.setBuyid(Integer.parseInt(processInstance.getBusinessKey()));
                buy.setUptype(1);
                upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }
                if (actBuy != 0) {
                    map.put("code", "101");
                    map.put("status", "提交");
                }
            } else if (roles.contains("采购经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_3").singleResult();
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setUpname(userServise.findbyid(buy.getBuyerid()).getRealname());
                act_buy.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(2);
                actBuy = actService.addActBuy(act_buy);
                if (actBuy != 0) {
                    map.put("code", "101");
                    map.put("status", "经理审批同意");
                }
                buy.setUptype(2);
                upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }

            } else if (roles.contains("总经理")) {
                taskService.complete(String.valueOf(taskId));
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_4").singleResult();
                HashMap<Object, Object> map1 = new HashMap<>();
                map1.put("id", processInstance.getBusinessKey());
                map1.put("needStatus", "审批完成");
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy = new Act_Buy();
                act_buy.setAuther(userServise.findbyid(buy.getBuyerid()).getRealname());
                act_buy.setUpname(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(3);
                buy.setUptype(3);
                upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }
                actBuy = actService.addActBuy(act_buy);
                if (actBuy != 0) {
                    map.put("code", "101");
                    map.put("status", "总经理审批通过");
                }
            }
        }
        return map;
    }

    /*驳回审批节点*/
    @RequiresPermissions("Manger:deleteprocess")
    @GetMapping("/deleteprocess")
    @ResponseBody
    public Map<String, Object> deleteprocess(Integer taskId, String text) {
        String upname = null;
        String auther = null;
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        Set<String> roles = userServise.findRoleByUserName(username);
        if (roles.contains("需求经理") || roles.contains("采购经理") || roles.contains("总经理")) {
            taskService.claim(String.valueOf(taskId), String.valueOf(userServise.findUser(username).getUserid()));
        }
        Task task = taskService.createTaskQuery().taskId(String.valueOf(taskId)).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String processDefinitionKey = processInstance.getProcessDefinitionKey();

        if (processDefinitionKey.contains("needAudit")) {
            if (roles.contains("需求经理")) {
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_3").singleResult();
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                upname = userServise.findbyid(need.getNeederid()).getRealname();
                auther = userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname();
                Act_Need act_need = new Act_Need();
                act_need.setUpname(upname);
                act_need.setAuther(auther);
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(4);
                need.setUptype(4);
                Integer actNeed = actService.addActNeed(act_need);
                Integer upneed = needService.updateStatus(need);
                map.put("code", "101");
                map.put("status", "审批驳回");
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "审批驳回");
                }
                String processInstanceId = task.getProcessInstanceId();
                runtimeService.deleteProcessInstance(processInstanceId, "processInstance delete");
            } else if (roles.contains("总经理")) {
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_4").singleResult();
                Need need = needService.findByNeedid(Integer.parseInt(processInstance.getBusinessKey()));
                upname = userServise.findbyid(need.getNeederid()).getRealname();
                auther = userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname();
                Act_Need act_need = new Act_Need();
                act_need.setUpname(upname);
                act_need.setAuther(auther);
                act_need.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_need.setStarttime(instance.getStartTime());
                act_need.setEndTime(instance.getEndTime());
                act_need.setText(text);
                act_need.setId(4);
                need.setUptype(4);
                Integer actNeed = actService.addActNeed(act_need);
                Integer upneed = needService.updateStatus(need);
                map.put("code", "101");
                map.put("status", "审批驳回");
                if (upneed != 0) {
                    map.put("success", "状态修改");
                }
                if (actNeed != 0) {
                    map.put("code", "101");
                    map.put("status", "审批驳回");
                }
                String processInstanceId = task.getProcessInstanceId();
                runtimeService.deleteProcessInstance(processInstanceId, "processInstance delete");
            }
        } else if (processDefinitionKey.contains("buyAudit")) {
            if (roles.contains("采购经理")) {
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_3").singleResult();
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                System.out.println(buy);
                Act_Buy act_buy = new Act_Buy();
                act_buy.setUpname(userServise.findbyid(buy.getBuyerid()).getRealname());
                act_buy.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(4);
                buy.setUptype(4);
                Integer actBuy = actService.addActBuy(act_buy);
                Integer upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }
                if (actBuy != 0) {
                    map.put("code", "101");
                    map.put("status", "审批驳回");
                }
                String processInstanceId = task.getProcessInstanceId();
                runtimeService.deleteProcessInstance(processInstanceId, "processInstance delete");
            } else if (roles.contains("总经理")) {
                HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId());
                HistoricActivityInstance instance = historicActivityInstanceQuery.taskAssignee(String.valueOf(userServise.findUser(username).getUserid())).activityId("_4").singleResult();
                Buy buy = buyService.findBuyById(Integer.parseInt(processInstance.getBusinessKey()));
                Act_Buy act_buy = new Act_Buy();
                act_buy.setUpname(userServise.findbyid(buy.getBuyerid()).getRealname());
                act_buy.setAuther(userServise.findbyid(Integer.parseInt(task.getAssignee())).getRealname());
                act_buy.setBusinessId(Integer.parseInt(processInstance.getBusinessKey()));
                act_buy.setStarttime(instance.getStartTime());
                act_buy.setEndTime(instance.getEndTime());
                act_buy.setText(text);
                act_buy.setId(4);
                buy.setUptype(4);
                Integer actBuy = actService.addActBuy(act_buy);
                Integer upbuy = buyService.updateStatus(buy);
                if (upbuy != 0) {
                    map.put("success", "状态修改");
                }
                if (actBuy != 0) {
                    map.put("code", "101");
                    map.put("status", "审批驳回");
                }
                String processInstanceId = task.getProcessInstanceId();
                runtimeService.deleteProcessInstance(processInstanceId, "processInstance delete");
            }
        }
        return map;
    }


    /**
     * 查看需求历史审批
     **/

    @RequiresPermissions("need:needHistory")
    @GetMapping("/findHistoty")
    @ResponseBody
    public Map<String, Object> findHistoty(int needid) {
        Map<String, Object> map = new HashMap<>();
        List<Act_Need> actNeedList = actService.findActNeed(needid);
        map.put("list", actNeedList);
        return map;
    }


    /**
     * 查看购买历史审批
     */
    @RequiresPermissions("buy:buyHistory")
    @GetMapping("/findHistotyBuy")
    @ResponseBody
    public Map<String, Object> findHistotyBuy(int buyid) {
        Map<String, Object> map = new HashMap<>();
        List<Act_Buy> actBuy = actService.findActBuy(buyid);
        map.put("list", actBuy);
        return map;
    }

    /**
     * 查看历史代办
     */

    @RequiresPermissions("needManager:findHistory")
    @PostMapping("/findFinishedNeed")
    @ResponseBody
    public Map<String, Object> findFinishedNeed(@RequestBody NeedVO needVO) {
        Map<String, Object> map = new HashMap<>();
        int limit = needVO.getPage();
        int page = needVO.getPage();
        int index = 1;
        int flag = 0;
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        Set<String> roles = userServise.findRoleByUserName(username);
        List<Need> needList = new ArrayList<>();
        Integer count = 0;
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("needAudit").singleResult();

        if (roles.contains("需求经理")) {
            List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processDefinitionId(String.valueOf(processDefinition.getId())).taskAssignee(String.valueOf(user.getUserid())).taskDefinitionKey("_3").list();
            for (HistoricTaskInstance instance : list) {
                String processInstanceId = instance.getProcessInstanceId();
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                String businessKey = historicProcessInstance.getBusinessKey();
                Need need = needService.findByNeedid(Integer.parseInt(businessKey));
                if (flag <= limit & index >= page) {
                    needList.add(need);
                    flag++;
                    index++;
                }
                count++;
            }
            map.put("count", count);
            map.put("page", needVO.getPage());
            map.put("limit", needVO.getPage());
            map.put("list", needList);
        } else if (roles.contains("总经理")) {
            if (needList.equals(null) || needList.size() == 0) {
                List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processDefinitionId(String.valueOf(processDefinition.getId())).taskAssignee(String.valueOf(user.getUserid())).activityId("_4").list();
                for (HistoricActivityInstance activityInstance : list) {
                    String processInstanceId = activityInstance.getProcessInstanceId();
                    HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                    String businessKey = historicProcessInstance.getBusinessKey();
                    Need need = needService.findByNeedid(Integer.parseInt(businessKey));
                    if (flag <= limit & index >= page) {
                        needList.add(need);
                        flag++;
                        index++;
                    }
                        count++;
                }
            } else {
                needList.clear();
                List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processDefinitionId(String.valueOf(processDefinition.getId())).taskAssignee(String.valueOf(user.getUserid())).activityId("_4").list();
                for (HistoricActivityInstance activityInstance : list) {
                    String processInstanceId = activityInstance.getProcessInstanceId();
                    HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                    String businessKey = historicProcessInstance.getBusinessKey();
                    Need need = needService.findByNeedid(Integer.parseInt(businessKey));
                    if (flag <= limit & index >= page) {
                        needList.add(need);
                        flag++;
                        index++;
                    }
                    count++;
                }
            }
            map.put("count", count);
            map.put("page", needVO.getPage());
            map.put("limit", needVO.getLimit());
            map.put("list", needList);
        }
        return map;
    }

    /**
     * 查看购买历史代办
     */
    @RequiresPermissions("buyManager:findHistory")
    @PostMapping("/findFinishedBuy")
    @ResponseBody
    public Map<String, Object> findFinishedBuy(@RequestBody BuyVo buyVo) {
        Map<String, Object> map = new HashMap<>();
        int limit = buyVo.getLimit();
        int page = buyVo.getPage();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        Set<String> roles = userServise.findRoleByUserName(username);
        List<Buy> buyList = new ArrayList<>();
        Integer count = 0;
        int index = 1;
        int flag = 0;
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("buyAudit").singleResult();
        if (roles.contains("采购经理")) {
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processDefinitionId(String.valueOf(processDefinition.getId())).taskAssignee(String.valueOf(user.getUserid())).activityId("_3").list();
            for (HistoricActivityInstance activityInstance : list) {
                String processInstanceId = activityInstance.getProcessInstanceId();
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                String businessKey = historicProcessInstance.getBusinessKey();
                Buy buy = buyService.findBuyById(Integer.parseInt(businessKey));
                if (flag <= limit & index >= page) {
                    buyList.add(buy);
                    flag++;
                    index++;
                }
                count++;
            }
            map.put("count", count);
            map.put("page", index);
            map.put("limit", flag);
            map.put("list", buyList);
        } else if (roles.contains("总经理")) {
            if (buyList.equals(null) || buyList.size() == 0) {
                List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processDefinitionId(String.valueOf(processDefinition.getId())).taskAssignee(String.valueOf(user.getUserid())).activityId("_4").list();
                for (HistoricActivityInstance activityInstance : list) {
                    String processInstanceId = activityInstance.getProcessInstanceId();
                    HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                    String businessKey = historicProcessInstance.getBusinessKey();
                    Buy buy = buyService.findBuyById(Integer.parseInt(businessKey));
                    if (flag <= limit & index >= page) {
                        buyList.add(buy);
                        flag++;
                        index++;
                    }
                    count++;
                }
            } else {
                buyList.clear();
                List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processDefinitionId(String.valueOf(processDefinition.getId())).taskAssignee(String.valueOf(user.getUserid())).activityId("_4").list();
                for (HistoricActivityInstance activityInstance : list) {
                    String processInstanceId = activityInstance.getProcessInstanceId();
                    HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                    String businessKey = historicProcessInstance.getBusinessKey();
                    Buy buy = buyService.findBuyById(Integer.parseInt(businessKey));
                    if (flag <= limit & index >= page) {
                        buyList.add(buy);
                        flag++;
                        index++;
                    }
                    count++;
                }
            }
            map.put("count", count);
            map.put("page", index);
            map.put("limit", flag);
            map.put("list", buyList);
        }
        return map;
    }

}




