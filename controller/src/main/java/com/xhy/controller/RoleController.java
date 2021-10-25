package com.xhy.controller;


import com.github.pagehelper.PageInfo;
import com.xhy.domain.*;
import com.xhy.service.PermissionService;
import com.xhy.service.RoleService;
import com.xhy.service.UserServise;
import com.xhy.vo.RolePermVO;
import com.xhy.vo.RoleVO;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/Role")
@Api(value = "/web",tags = "角色管理接口")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserServise userServise;


    @RequiresPermissions("admin:listRole")
    @GetMapping("/listRole")
    public @ResponseBody Map<String,Object> ListRole(RoleVO roleVO){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> list = roleService.findAllRole(roleVO);
        PageInfo pageInfo = new PageInfo(list);
        int pageNum = pageInfo.getPageNum();
        long total = pageInfo.getTotal();
        map.put("page", pageNum);
        map.put("list", list);
        map.put("count",total);
        return map;
    }

    @RequiresPermissions("admin:addRole")
    @PostMapping ("/addRole")
    public @ResponseBody Map<String,Object> addRole(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        int add = roleService.addRole(role);
        System.out.println(add);
        if(add!=0){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

    @RequiresPermissions("admin:updateRole")
    @PostMapping("/updateRole")
    public @ResponseBody Map<String,Object> updateRole(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        int update = roleService.updateRole(role);
        if(update!=0){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

    @RequiresPermissions("admin:deleteRole")
    @GetMapping("/deleteRole")
    public @ResponseBody Map<String,Object> deleteRole(int roleId){
        Map<String,Object> map = new HashMap<>();
        int delete = roleService.deleteRole(roleId);
        Boolean aBoolean = roleService.deleteRolePerm(roleId);
        if(delete!=0 & aBoolean){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }


    /**
     * 修改角色状态
     * */
    @RequiresPermissions("admin:updateRoleStatus")
    @PostMapping("/updateRoleStatus")
    @ResponseBody
    public Map<String,Object> updateRoleStatus(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        Integer status = roleService.updateRoleStatus(role);
        if(status!=0){
            map.put("code","101");
        }
        else map.put("code","101");
        return map;
    }

    /*
     * 拿到角色权限关联
     * */
    @RequiresPermissions("admin:getRolePerm")
    @GetMapping("/getRolePerm")
    @ResponseBody
    public Map<String,Object> getRolePerm(int roleId){
        Map<String,Object> map = new HashMap<>();
        //查询所有权限
       List<Permission> permissions = permissionService.findPermission();
        List<RolePerm> rolePerm = roleService.getRolePerm(roleId);
        if(permissions != null){
            map.put("code","101");
            map.put("roles",permissions);
            map.put("rolePerm",rolePerm);
        }else
        {
            map.put("code","102");
        }
        return map;
    }


    /*
     * 处理添加数据
     * */

    @RequiresPermissions("admin:addRolePerm")
    @PostMapping("/addRolePerm")
    @ResponseBody
    public Map<String,Object> AddUserRole(@RequestBody RolePermVO rolePermvo){
        Map<String,Object> map = new HashMap<>();
        List<Integer> permIds = rolePermvo.getPermId();
        RolePerm rolePerm = new RolePerm();
        rolePerm.setRoleId(rolePermvo.getRoleId());
        List<RolePerm> rolePerm1 = roleService.getRolePerm(rolePermvo.getRoleId());
        if(!rolePerm1.isEmpty()){
            roleService.deleteRolePerm(rolePermvo.getRoleId());
        }
        for(Integer permId: permIds){
            rolePerm.setPermId(permId);
            roleService.addRolePerm(rolePerm);
        }
        if(rolePerm1.size()!=0) {
            map.put("code", "101");
        }
        return map;
    }

//

}
