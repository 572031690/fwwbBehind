package com.xhy.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import com.xhy.domain.Need;
import com.xhy.domain.Role;
import com.xhy.domain.User;
import com.xhy.service.NeedService;
import com.xhy.service.UserServise;
import com.xhy.vo.NeedVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webneed")
public class NeedController {

    @Autowired
    NeedService needService;

    @Autowired
    UserServise userServise;

    @RequiresPermissions("needer:listNeed")
    @GetMapping(value = "/findAllNeed")
    public @ResponseBody Map<String,Object> findAllNeed(NeedVO needVO)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        List<Need> needList = needService.findAllNeed(needVO);
        PageInfo pageInfo = new PageInfo(needList);
        int pageNum = pageInfo.getPageNum();
        long total = pageInfo.getTotal();
        map.put("list",needList);
        map.put("page",pageNum);
        map.put("count",total);
        return map;
    }

    @RequiresPermissions("needer:updateNeed")
    @RequestMapping(value = "/updateNeed",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateNeed(@RequestBody Need need){
        Map<String,Object> map = new HashMap<String,Object>();
        if (need != null) {
            Integer integer = needService.updateNeed(need);
            if (integer != 0) {
                map.put("code", "101");
                return map;
            } else {
                map.put("code", "102");
                return map;
            }
        } else {
            map.put("code", "102");
            return map;
        }
    }



    @RequiresPermissions("needer:addNeed")
    @RequestMapping(value = "/addNeed",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> addNeed(@RequestBody Need need){
        Map<String,Object> map = new HashMap<String,Object>();
        if (need != null) {
            Integer integer = needService.addNeed(need);
            if (integer != 0) {
                map.put("code", "101");
                return map;
            } else {
                map.put("code", "102");
                return map;
            }
        } else {
            map.put("code", "102");
            return map;
        }
    }

    @RequiresPermissions("needer:deleteNeed")
    @RequestMapping(value = "/deleteNeed",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> deleteNeed(int needid){
        Map<String,Object> map = new HashMap<String,Object>();
            Integer integer = needService.deleteNeed(needid);
            if (integer != 0) {
                map.put("code", "101");
                return map;
            } else {
                map.put("code", "102");
                return map;
            }
    }



    /*查看需求相关信息*/
    @GetMapping("/getNeedCount")
    @ResponseBody
    public Map<String,Object> getNeedCount(){
        Map<String,Object> map = new HashMap<>();
        List<Need> needList = needService.findNeed();
        Integer approve = 0;
        Integer reject = 0;
        Integer sum = 0;
        for(Need need : needList){
            if(need.getUptype()==3){
                approve++;
            }
            else if(need.getUptype()==4){
                reject++;
            }
            sum++;
        }
        map.put("approve",approve);
        map.put("reject",reject);
        map.put("sum",sum);
        return map;
    }



//    @RequiresPermissions("needer:listNeed")
//    @RequestMapping(value = "/findHistroy",method = RequestMethod.GET)
//    public @ResponseBody Map<String,Object> findHistroy( NeedVO needVO)
//    {
//        Map<String,Object> map = new HashMap<String,Object>();
//        Map<String, Object> histroy = needService.findHistroy(needVO);
//        map.put("list",histroy.get("list"));
//        map.put("count",histroy.get("count"));
//        map.put("page",histroy.get("page"));
//        map.put("limit",histroy.get("limit"));
//        return map;
//    }



}
