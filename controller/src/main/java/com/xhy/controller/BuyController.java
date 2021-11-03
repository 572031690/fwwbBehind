package com.xhy.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xhy.domain.Buy;
import com.xhy.domain.Need;
import com.xhy.domain.User;
import com.xhy.service.BuyService;
import com.xhy.service.UserServise;
import com.xhy.vo.BuyVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webbuy")
public class BuyController {

    @Autowired
    BuyService buyService;

    @Autowired
    UserServise userServise;

    @RequiresPermissions("buyer:listBuy")
    @PostMapping("/findAllBuy")
    public @ResponseBody
    Map<String, Object> findAllBuy(@RequestBody BuyVo buyVo) {
        System.out.println(buyVo);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Buy> buyList = buyService.findAllBuy(buyVo);
        for(Buy b : buyList){
            if(b.getBuyerid()==0){
                Subject subject = SecurityUtils.getSubject();
                String username = String.valueOf(subject.getPrincipals());
                User user = userServise.findUser(username);
                b.setBuyerid(user.getUserid());
                buyService.updateBuy(b);
            }
        }
        PageInfo pageInfo = new PageInfo(buyList);
        long total = pageInfo.getTotal();
        int pageNum = pageInfo.getPageNum();
        map.put("list", buyList);
        map.put("page", pageNum);
        map.put("count", total);
        return map;
    }

    @RequiresPermissions("buyer:updateBuy")
    @RequestMapping(value = "/updateBuy", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateBuy(@RequestBody Buy buy) {
        Map<String, Object> map = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        buy.setBuyerid(user.getUserid());
        if (buy != null) {
            Integer updatebuy = buyService.updateBuy(buy);
            if (updatebuy != 0) {

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

//    @RequestMapping(value = "/findBuyById", method = RequestMethod.GET)
//    public @ResponseBody
//    Map<String, Object> findBuyById(int page, int limit, int buyid) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        List<Buy> buyList1 = buyService.findBuyById(0, 0, buyid);
//        PageInfo pageInfo1 = new PageInfo(buyList1);
//        int count = pageInfo1.getSize();
//        map.put("count", count);
//        List<Buy> buyList2 = buyService.findBuyById(page, limit, buyid);
//        PageInfo pageInfo2 = new PageInfo(buyList2);
//        int pageNum = pageInfo2.getPageNum();
//        map.put("list", buyList2);
//        map.put("page", pageNum);
//        return map;
//    }

    @RequiresPermissions("buyer:addBuy")
    @RequestMapping(value = "/addBuy", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addBuy(@RequestBody Buy buy) {
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getPrincipals());
        User user = userServise.findUser(username);
        buy.setBuyerid(user.getUserid());
        Map<String, Object> map = new HashMap<String, Object>();
        if (buy != null) {
            Integer addBuy = buyService.addBuy(buy);
            if (addBuy != 0) {
                map.put("code", "101");
                return map;
            } else {
                map.put("code", "102");
                return map;
            }
        } else {
            map.put("code", "103");
            return map;
        }
    }

    @RequiresPermissions("buyer:deleteBuy")
    @RequestMapping(value = "/deleteBuy", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> deleteBuy(int buyid) {
            Map<String, Object> map = new HashMap<String, Object>();
            Integer deleteBuy =  buyService.deleteBuy(buyid);
            if(deleteBuy != 0) {
                map.put("code", "101");
                return map;
            } else {
                map.put("code", "102");
                return map;
            }
    }


    /*查看需求相关信息*/
    @RequiresPermissions("buy:getBuyCount")
    @GetMapping("/getBuyCount")
    @ResponseBody
    public Map<String,Object> getBuyCount(){
        Map<String,Object> map = new HashMap<>();
        List<Buy> buyList = buyService.findBuy();
        Integer approve = 0;
        Integer reject = 0;
        Integer sum = 0;
        for(Buy buy : buyList){
            if(buy.getUptype()==3){
                approve++;
            }
            else if(buy.getUptype()==4){
                reject++;
            }
            sum++;
        }
        map.put("approve",approve);
        map.put("reject",reject);
        map.put("sum",sum);
        return map;
    }

    /*采购审批结果导出*/
    @GetMapping("/buyResult")
    public void buyResult(HttpServletResponse response){
        List<Buy> buy = buyService.findBuy();
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("采购计划审批结果", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Buy.class).autoCloseStream(Boolean.FALSE).sheet("采购审批结果")
                    .doWrite(buy);
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
