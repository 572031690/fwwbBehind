package com.xhy.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import com.xhy.domain.*;
import com.xhy.service.*;
import com.xhy.vo.InRepositoryVO;
import com.xhy.vo.OutRepositoryVO;
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
@RequestMapping("/repository")
public class InOutRepositoryController {
    @Autowired
    InRepositoryService inRepositoryService; //入库管理功能模块

    @Autowired
    OutRepositoryService outRepositoryService; //出库管理功能模块

    @Autowired
    DepositoryService depositoryService; //仓库管理模块

    @Autowired
    NeedService needService; //需求管理模块

    @Autowired
    BuyService buyService; //采购管理模块


    /*******************入库管理功能接口*****************/

    /**
     * 查询入库列表
     */
    @RequiresPermissions("storekeeper:findInRepositoryList")
    @GetMapping("/findInRepositoryList")
    @ResponseBody
    public Map<String, Object> findInRepositoryList(InRepositoryVO inRepositoryVO) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        List<InRepository> list = inRepositoryService.findInRepository(inRepositoryVO);
        PageInfo pageInfo = new PageInfo(list);
        int pageNum = pageInfo.getPageNum();
        long total = pageInfo.getTotal();
        map.put("list", list);
        map.put("page", pageNum);
        map.put("count", total);
        return map;
    }


    /**
     * 添加入库信息
     * 所有属性都要填
     */
    @RequiresPermissions("storekeeper:addInRepository")
    @PostMapping("/addInRepository")
    @ResponseBody
    public Map<String, Object> addInRepository(@RequestBody InRepository inRepository) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        Integer integer = inRepositoryService.addInRepository(inRepository);
        if (integer != 0) {
            map.put("code", "101");
        } else map.put("code", "102");
        return map;
    }

    /**
     * 修改入库信息
     */
    @RequiresPermissions("storekeeper:updateInRepository")
    @PostMapping("/updateInRepository")
    @ResponseBody
    public Map<String, Object> updateInRepository(@RequestBody InRepository inRepository) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        Integer integer = inRepositoryService.updateInRepository(inRepository);
        if (integer != 0) {
            map.put("code", "101");
        } else map.put("code", "102");
        return map;
    }

    /**
     * 删除入库信息
     */
    @RequiresPermissions("storekeeper:deleteInRepository")
    @GetMapping("/deleteInRepository")
    @ResponseBody
    public Map<String, Object> deleteInRepository(int id) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        Integer integer = inRepositoryService.deleteInRepository(id);
        if (integer != 0) {
            map.put("code", "101");
        } else map.put("code", "102");
        return map;
    }


    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date date = new Date();
    /**
     * 入库操作
     */
    @RequiresPermissions("storekeeper:RepositoryIn")
    @PostMapping("/RepositoryIn")
    @ResponseBody
    public Map<String, Object> RepositoryIn(@RequestBody InRepository inRepository) {
        Map<String, Object> map = new HashMap<>();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String date = dateFormat.format(new Date());
        int num = inRepository.getNum();
        Depository depository = depositoryService.findByName(inRepository.getName());
        int stock = depository.getStock();
        int addNum = stock + num;
        depository.setStock(addNum);
        Integer integer = depositoryService.updataDepository(depository);
        if (integer != 0) {
            Buy buy = buyService.findBuyById(inRepository.getBuyid());
            Depository depository_new = depositoryService.findByName(inRepository.getName());
            if(depository_new.getStock() >= buy.getNum()){
                buy.setBuytype(1); //buytype=1表示完成采购
                buy.setArrivaltime(date);
                Integer status = buyService.updateBuy(buy);
                if(status != 0){
                    Need need = needService.findByNeedid(buy.getNeedid());
                    need.setPlanName("库存供应");
                    needService.updateStatus(need);
                    map.put("code", "101");
                }
                else map.put("code", "102");
            }
        } else {
            map.put("code", "102");
        }
        return map;
    }

    /**********************出库管理功能接口*******************/

    /**
     * 查询出库列表
     */
    @RequiresPermissions("storekeeper:findOutRepositoryList")
    @GetMapping("/findOutRepositoryList")
    @ResponseBody
    public Map<String, Object> findOutRepositoryList(OutRepositoryVO outRepositoryVO) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        List<OutRepository> list = outRepositoryService.findOutRepository(outRepositoryVO);
        PageInfo pageInfo = new PageInfo(list);
        int pageNum = pageInfo.getPageNum();
        long total = pageInfo.getTotal();
        map.put("list", list);
        map.put("page", pageNum);
        map.put("count", total);
        return map;
    }


    /**
     * 添加出库信息
     * 所有属性都要填
     */
    @RequiresPermissions("storekeeper:addOutRepository")
    @PostMapping("/addOutRepository")
    @ResponseBody
    public Map<String, Object> addOutRepository(@RequestBody OutRepository outRepository) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        Integer integer = outRepositoryService.addOutRepository(outRepository);
        if (integer != 0) {
            map.put("code", "101");
        } else map.put("code", "102");
        return map;
    }

    /**
     * 修改出库信息
     */
    @RequiresPermissions("storekeeper:updateOutRepository")
    @PostMapping("/updateOutRepository")
    @ResponseBody
    public Map<String, Object> updateOutRepository(@RequestBody OutRepository outRepository) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        Integer integer = outRepositoryService.updateOutRepository(outRepository);
        if (integer != 0) {
            map.put("code", "101");
        } else map.put("code", "102");
        return map;
    }

    /**
     * 删除出库信息
     */
    @RequiresPermissions("storekeeper:deleteOutRepository")
    @GetMapping("/deleteOutRepository")
    @ResponseBody
    public Map<String, Object> deleteOutRepository(int id) {
        Map<String, Object> map = new HashMap<>();  //用于存放回传的数据
        Integer integer = outRepositoryService.deleteOutRepository(id);
        if (integer != 0) {
            map.put("code", "101");
        } else map.put("code", "102");
        return map;
    }


    /**
     * 出库操作
     */
    @RequiresPermissions("storekeeper:RepositoryOut")
    @PostMapping("/RepositoryOut")
    @ResponseBody
    public Map<String, Object> RepositoryOut(@RequestBody OutRepository outRepository) {
        Map<String, Object> map = new HashMap<>();
        int num = outRepository.getNum();
        Depository depository = depositoryService.findByName(outRepository.getName());
        int stock = depository.getStock();
        int closeNum = stock - num;
        depository.setStock(closeNum);
        Integer integer = depositoryService.updataDepository(depository);
        if(integer!=0){
            Need need = needService.findByNeedid(outRepository.getNeedid());
            need.setApprovaltype(1); //approvaltype=1表示完成供应
            Integer status = needService.updateNeed(need);
            if(status != 0){
                map.put("code","101");
            }else map.put("code","102");
        }
        else map.put("code","102");
        return map;
    }
}
