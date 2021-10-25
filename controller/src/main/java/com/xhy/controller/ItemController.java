package com.xhy.controller;


import com.github.pagehelper.PageInfo;
import com.xhy.domain.Item;
import com.xhy.service.ItemService;
import com.xhy.vo.ItemVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webitem")
public class ItemController {

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/findAllitem", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findAllitem(ItemVO itemVO) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Item> itemList = itemService.findAllItem(itemVO);
        PageInfo pageInfo = new PageInfo(itemList);
        int pageNum = pageInfo.getPageNum();
        long total = pageInfo.getTotal();
        map.put("count",total);
        map.put("list", itemList);
        map.put("page", pageNum);
        return map;


    }

    @ResponseBody
    @GetMapping("/findItemName")
    public Map<String,Object> findItemName( Integer itemid){
        Map<String ,Object> map = new HashMap<>();
        List<Item> item = itemService.findItemName(itemid);
        if(!item.equals(null) || item.size()==0){
            map.put("code","101");
            map.put("list",item);
        }else{
            map.put("code","102");
        }
        return map;
    }

    @RequiresPermissions("item:addItem")
    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addItem(@RequestBody Item item) {
        Map<String, Object> map = new HashMap<>();
        if (item != null) {
            Integer integer = itemService.addItem(item);
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

    @RequiresPermissions("item:updateItem")
    @RequestMapping(value = "/updateItem", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateItem(@RequestBody Item item) {
        Map<String, Object> map = new HashMap<>();
        if (item != null) {
            Integer integer = itemService.updateItem(item);
            if (integer != 0) {
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
    @RequiresPermissions("item:deleteItem")
    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> deleteItem(Integer itemid) {
        Map<String, Object> map = new HashMap<>();
        Integer integer = itemService.deleteItem(itemid);
        if (integer != 0) {
            map.put("code", "101");
            return map;
        } else {
            map.put("code", "102");
            return map;
        }
    }
}
