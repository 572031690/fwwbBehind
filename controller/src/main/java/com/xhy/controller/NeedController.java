package com.xhy.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xhy.domain.Need;
import com.xhy.service.NeedService;
import com.xhy.service.UserServise;
import com.xhy.vo.NeedVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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
    @PostMapping ("/findAllNeed")
    public @ResponseBody Map<String,Object> findAllNeed(@RequestBody NeedVO needVO)
    {

        System.out.println(needVO);
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
    @RequiresPermissions("need:getNeedCount")
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


    /*需求审批结果导出*/
    @GetMapping("/needResult")
    public void needResult(HttpServletResponse response){
        List<Need> need = needService.findNeed();
        try {

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("需求计划审批结果", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Need.class).autoCloseStream(Boolean.FALSE).sheet("需求审批结果")
                    .doWrite(need);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String,Object> map =new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }




}
