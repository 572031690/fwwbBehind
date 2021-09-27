package com.xhy.controller;

import com.github.pagehelper.PageInfo;
import com.xhy.domain.Buy;
import com.xhy.service.BuyService;
import com.xhy.vo.BuyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webbuy")
public class BuyController {

    @Autowired
    BuyService buyService;

    @RequestMapping(value = "/findAllBuy", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findAllBuy(BuyVo buyVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        BuyVo buyVo1 = new BuyVo();
        buyVo1.setLimit(0);
        buyVo1.setPage(0);
        buyVo1.setSearchName(buyVo.getSearchName());
        buyVo1.setSelectName(buyVo.getSelectName());
        List<Buy> buyList1 = buyService.findAllBuy(buyVo1);
        PageInfo pageInfo1 = new PageInfo(buyList1);
        int count = pageInfo1.getSize();
        map.put("count", count);
        List<Buy> buyList2 = buyService.findAllBuy(buyVo);
        PageInfo pageInfo2 = new PageInfo(buyList2);
        int pageNum = pageInfo2.getPageNum();
        map.put("list", buyList2);
        map.put("page", pageNum);
        return map;
    }

    @RequestMapping(value = "/updateBuy", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateBuy(@RequestBody Buy buy) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (buy != null) {
            Integer updatebuy = buyService.updateBuy(buy);
            if (updatebuy != null) {

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

    @RequestMapping(value = "/findBuyById", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findBuyById(int page, int limit, int buyid) {

        Map<String, Object> map = new HashMap<String, Object>();
        List<Buy> buyList1 = buyService.findBuyById(0, 0, buyid);
        PageInfo pageInfo1 = new PageInfo(buyList1);
        int count = pageInfo1.getSize();
        map.put("count", count);
        List<Buy> buyList2 = buyService.findBuyById(page, limit, buyid);
        PageInfo pageInfo2 = new PageInfo(buyList2);
        int pageNum = pageInfo2.getPageNum();
        map.put("list", buyList2);
        map.put("page", pageNum);
        return map;
    }

    @RequestMapping(value = "/addBuy", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addBuy(@RequestBody Buy buy) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (buy != null) {
            Integer addBuy = buyService.addBuy(buy);
            if (addBuy != null) {
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

    @RequestMapping(value = "/deleteBuy", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> deleteBuy(int buyid) {
            Map<String, Object> map = new HashMap<String, Object>();
            Integer deleteBuy =  buyService.deleteBuy(buyid);
            if(deleteBuy != null) {
                map.put("code", "101");
                return map;
            } else {
                map.put("code", "102");
                return map;
            }
    }
}
