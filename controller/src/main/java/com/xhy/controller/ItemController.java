package com.xhy.controller;


import com.github.pagehelper.PageInfo;
import com.xhy.domain.Item;
import com.xhy.domain.User;
import com.xhy.service.ItemService;
import com.xhy.vo.ItemVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/webitem")
public class ItemController {

    @Autowired
    ItemService itemService;

//    @RequiresPermissions("item:listItem")
    @RequestMapping(value = "/findAllitem", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findAllitem(ItemVO itemVO) {
        Map<String, Object> map = new HashMap<String, Object>();
        ItemVO itemCount = new ItemVO();
        itemCount.setPage(0);
        itemCount.setLimit(0);
        itemCount.setSearchName(itemVO.getSearchName());
        itemCount.setSelectName(itemVO.getSelectName());
        List<Item> itemList = itemService.findAllItem(itemCount);
        PageInfo pageInfo1 = new PageInfo(itemList);
        int count = pageInfo1.getSize();
        map.put("count", count);
        List<Item> itemList2 = itemService.findAllItem(itemVO);
        PageInfo pageInfo2 = new PageInfo(itemList2);
        int pageNum = pageInfo2.getPageNum();
        map.put("list", itemList2);
        map.put("page", pageNum);

        return map;


    }

    @RequestMapping(value = "/findItemById", method = RequestMethod.GET)
    public @ResponseBody
    Item findItemById(int page, int limit, String itemid) {

        return itemService.findItemById(page, limit, itemid);
    }

//    @RequiresPermissions("item:addItem")
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

//    @RequiresPermissions("item:udpateItem")
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
//    @RequiresPermissions("item:deleteItem")
    @RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> deleteItem(String itemid) {
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
