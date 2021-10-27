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
        int count = 0; //入库总量
        int num = inRepository.getNum();
        Depository depository = depositoryService.findByName(inRepository.getName());
        Buy buy = buyService.findBuyById(inRepository.getBuyid());
        /*获取入库表中记录*/
        InRepositoryVO inRepositoryVO = new InRepositoryVO();
        inRepositoryVO.setSelectName(String.valueOf(inRepository.getBuyid()));
        List<InRepository> inRepositoryList = inRepositoryService.findInRepository(inRepositoryVO);
        for(InRepository i : inRepositoryList){  //循环获取入库表中的入库数量
            count = count + i.getNum();  //入库总量 = 每次入库量之和
        }
        int needNum = buy.getNum();  //采购需求数量
        int stock = depository.getStock(); //仓库当前存量
        int totalStock  =  depository.getTotalstock(); //仓库总库存量
        if(count + stock <= totalStock && needNum <=totalStock)  //入库总量+当前库存量要小于库存总量
        {
            if (count < needNum) {   //入库总量<采购需求数量
                int addNum = stock + num;
                depository.setStock(addNum);
            } else if (count >= needNum) {
                int addNum = stock + num;
                depository.setStock(addNum);
                buy.setBuytype(2); //buytype=1表示完成采购

            }
            Integer integer = depositoryService.updataDepository(depository);
            if (integer != 0) {
                Depository depository_new = depositoryService.findByName(inRepository.getName());
                if (depository_new.getStock() >= buy.getNum()) {
                    buy.setArrivaltime(date);
                    Integer status = buyService.updateBuy(buy);
                    if (status != 0) {
                        Need need = needService.findByNeedid(buy.getNeedid());
                        need.setPlanName(2);
                        needService.updateStatus(need);
                        map.put("code", "101");
                    } else {
                        map.put("code", "102");
                        map.put("errot","采购状态修改失败");
                    }
                }
            } else {
                map.put("code", "102");
                map.put("error","仓库库存修改失败");
            }
        }else {
            map.put("code","102");
            map.put("error","入库数量超出或采购数量出错");
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
        int count = 0;  //出库总量
        int num = outRepository.getNum();
        Depository depository = depositoryService.findByName(outRepository.getName());
        Need need = needService.findByNeedid(outRepository.getNeedid());
        int neednum = need.getNeednum(); //需求数量
        int stock = depository.getStock();//库存数量
        int totalStock = depository.getTotalstock();//库存总量
        /*获取出库表中记录*/
        OutRepositoryVO outRepositoryVO = new OutRepositoryVO();
        outRepositoryVO.setSelectName(String.valueOf(outRepository.getNeedid()));
        List<OutRepository> outList = outRepositoryService.findOutRepository(outRepositoryVO);
        for (OutRepository repository : outList) {
            count = count + repository.getNum(); //出库总量 = 出库表中的数量总和
        }
        if (count <= stock || count <= totalStock) {   //判断出库总量是否超过当前库存量和库存总量
            if (count < neednum) {          //出库总量小于需求数量
                int closeNum = stock - num;
                depository.setStock(closeNum);
            } else if (count > neednum) {     //出库总量大于需求数量
                int closeNum = stock - num + (count - neednum);  //将多余的部分重新分配到仓库中
                depository.setStock(closeNum);
                need.setApprovaltype(2); //approvaltype=1表示完成供应
            } else if (count == neednum) {   //出库总量等于需求数量
                int closeNum = stock - num;
                depository.setStock(closeNum);
                need.setApprovaltype(1);//approvaltype=1表示完成供应
            }
            Integer integer = depositoryService.updataDepository(depository);
            if (integer != 0) {
                if (count >= neednum) {
                    Integer status = needService.updateNeed(need);
                    if (status != 0) {
                        map.put("code", "101");

                    } else {
                        map.put("code", "102");
                        map.put("error", "需求状态修改失败");
                    }
                }
            } else {
                map.put("code", "102");
                map.put("erroe","仓库修改失败");
            }
        } else {
            map.put("code", "102");
            map.put("error", "输入的数量超出当前库存或总库存");
        }
        return map;
    }
}
