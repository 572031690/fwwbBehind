package com.xhy.controller;

import com.github.pagehelper.PageInfo;
import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Perm")
public class PermissionController {

    @Autowired
    PermissionService permissionService;


//    @RequiresPermissions("admin:listPerm")
    @GetMapping("/listPerm")
    public @ResponseBody
    Map<String,Object> ListPerm(Integer page, Integer limit, String name){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Permission> list1 = permissionService.findAllPermission(0,0,name);
        PageInfo pageInfo1 = new PageInfo(list1);
        int count = pageInfo1.getSize();
        map.put("count", count);
        List<Permission> list2 = permissionService.findAllPermission(page,limit,name);
        PageInfo pageInfo2 = new PageInfo(list2);
        int pageNum = pageInfo2.getPageNum();
        map.put("page", pageNum);
        map.put("list", list2);
        return map;
    }

//    @RequiresPermissions("admin:addPerm")
    @PostMapping("/addPerm")
    public @ResponseBody Map<String,Object> addPerm(@RequestBody Permission permission){
        Map<String,Object> map = new HashMap<>();
        String add = permissionService.addPermission(permission);
        if(add!=null || add!=""){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

//    @RequiresPermissions("admin:updatePerm")
    @PostMapping("/updatePerm")
    public @ResponseBody Map<String,Object> updatePerm(@RequestBody Permission permission){
        Map<String,Object> map = new HashMap<>();
        String update = permissionService.updatePermission(permission);
        if(update!=null || update!=""){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

//    @RequiresPermissions("admin:deletePerm")
    @GetMapping("/deletePerm")
    public @ResponseBody Map<String,Object> deletePerm(int id){
        Map<String,Object> map = new HashMap<>();
        String delete = permissionService.deletePermission(id);
        if(delete!=null || delete!=""){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }



}
