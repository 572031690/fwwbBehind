package com.xhy.controller;

import com.github.pagehelper.PageInfo;
import com.xhy.domain.Depository;
import com.xhy.service.DepositoryService;
import com.xhy.vo.DepositoryVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/depositroy")
public class DepositoryController {

    @Autowired
    DepositoryService depositoryService;

    Map<String,Object> map = new HashMap<>();
    /**
     * 查看仓库信息
     * */
    @RequiresPermissions("depository:findDepository")
    @GetMapping("/findDepository")
    @ResponseBody
    public Map<String,Object>  findDepository(DepositoryVO depositoryVO){
        List<Depository> depositories = depositoryService.findDepository(depositoryVO);
        PageInfo pageInfo = new PageInfo(depositories);
        int pageNum = pageInfo.getPageNum();
        long total = pageInfo.getTotal();
        map.put("count",total);
        map.put("pageNum",pageNum);
        map.put("list",depositories);
        return map;
    }
    
    /**
     * 添加仓库信息
     * */
    @RequiresPermissions("depository:addDepository")
    @PostMapping("/addDepository")
    @ResponseBody
    public Map<String,Object> addDepository(@RequestBody Depository depository){
        Integer status = depositoryService.addDepository(depository);
        if(status != 0){
            map.put("code","101");
        }
        else{
            map.put("code","102");
        }
        return map;
    }
    
    /**
     * 修改仓库信息
     * */
    @RequiresPermissions("depository:updateDepository")
    @PostMapping("/updateDepository")
    @ResponseBody
    public Map<String,Object> updateDepository(@RequestBody Depository depository){
        Integer status = depositoryService.updataDepository(depository);
        if(status!=0)
        {
            map.put("code","101");
        }
        else
        {
            map.put("code","102");
        }
        return map;
    }

    /**
     * 删除仓库信息
     * */
    @RequiresPermissions("depository:deleteDepository")
    @GetMapping("/deleteDepository")
    @ResponseBody
    public Map<String,Object> deleteDepository(Integer id){
        Integer status = depositoryService.deleteDepository(id);
        if(status!=0)
        {
            map.put("code","101");
        }
        else
        {
            map.put("code","102");
        }
        return map;
    }

}
