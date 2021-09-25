package com.xhy.controller;


import com.xhy.domain.Item;
import com.xhy.service.ItemService;
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
    List<Item> findAllitem(int page, int limit, String itemtype) {


        List<Item> itemList = itemService.findAllItem(page, limit, itemtype);

        return itemList;

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
            if (integer != null) {
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

//    @RequiresPermissions("item:udpateItem")
    @RequestMapping(value = "/updateItem", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateItem(@RequestBody Item item) {
        Map<String, Object> map = new HashMap<>();
        if (item != null) {
            Integer integer = itemService.updateItem(item);
            if (integer != null) {
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
        if (integer != null) {
            map.put("code", "101");
            return map;
        } else {
            map.put("code", "102");
            return map;
        }
    }
}
