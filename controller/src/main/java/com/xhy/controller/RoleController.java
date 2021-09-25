package com.xhy.controller;


import com.github.pagehelper.PageInfo;
import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.domain.UserRole;
import com.xhy.service.PermissionService;
import com.xhy.service.RoleService;
import com.xhy.service.UserServise;
import com.xhy.vo.RolePermVO;
import com.xhy.vo.UserRoleVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/Role")
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserServise userServise;


//    @RequiresPermissions("admin:listRole")
    @GetMapping("/listRole")
    public @ResponseBody Map<String,Object> ListRole(Integer page, Integer limit, String rolename){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> list1 = roleService.findAllRole(0,0,rolename);
        PageInfo pageInfo1 = new PageInfo(list1);
        int count = pageInfo1.getSize();
        map.put("count", count);
        List<Role> list2 = roleService.findAllRole(page, limit, rolename);
        PageInfo pageInfo2 = new PageInfo(list2);
        int pageNum = pageInfo2.getPageNum();
        map.put("page", pageNum);
        map.put("list", list2);
        return map;
    }

//    @RequiresPermissions("admin:addRole")
    @PostMapping ("/addRole")
    public @ResponseBody Map<String,Object> addRole(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        String add = roleService.addRole(role);
        if(add!=null || add!=""){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

//    @RequiresPermissions("admin:updateRole")
    @PostMapping("/updateRole")
    public @ResponseBody Map<String,Object> updateRole(@RequestBody Role role){
        Map<String,Object> map = new HashMap<>();
        String update = roleService.updateRole(role);
        if(update!=null || update!=""){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

//    @RequiresPermissions("admin:deleteRole")
    @GetMapping("/deleteRole")
    public @ResponseBody Map<String,Object> deleteRole(int roleId){
        Map<String,Object> map = new HashMap<>();
        String delete = roleService.deleteRole(roleId);
        roleService.deleteRolePerm(roleId);
        if(delete!=null || delete!=""){
            map.put("code","101");
        }else {
            map.put("code","102");
        }
        return map;
    }

    /*
     * 拿到角色表中所有角色
     * */
//    @RequiresPermissions("admin:getRolePerm")
    @GetMapping("/getRolePerm")
    public Map<String,Object> getRolePerm(String username){
        Map<String,Object> map = new HashMap<>();
        //查询所有角色
       List<Permission> permissions = permissionService.findPermission();
        Set<String> permissionByUserName = userServise.findPermissionByUserName(username);
        if(permissions != null){
            map.put("code","101");
            map.put("roles",permissions);
            map.put("rolePerm",permissionByUserName);
        }else
        {
            map.put("code","102");
        }
        return map;
    }


    /*
     * 处理添加数据
     * */

//    @RequiresPermissions("admin:addRolePerm")
    @PostMapping("/addRolePerm")
    public Map<String,Object> AddUserRole(@RequestBody RolePermVO rolePermvo){
        Map<String,Object> map = new HashMap<>();
        List<Integer> permIds = rolePermvo.getPermId();
        RolePerm rolePerm = new RolePerm();
       rolePerm.setRoleId(rolePermvo.getRoleId());
        for(Integer permId: permIds){

            rolePerm.setPermId(permId);
            roleService.addRolePerm(rolePerm);
        }
        map.put("code","101");
        return map;
    }

    /**
     * 修改用户角色关联
     * */
//    @RequiresRoles("admin:updateRolePerm")
    @PostMapping("/updateRolePerm")
    @ResponseBody
    public Map<String,Object> updateRolePerm(@RequestBody RolePermVO rolePermvo){
        Map<String,Object> map = new HashMap<>();
        List<Integer> permIds = rolePermvo.getPermId();
        RolePerm rolePerm = new RolePerm();
        rolePerm.setRoleId(rolePermvo.getRoleId());
        for(Integer permId: permIds){

            rolePerm.setPermId(permId);
            roleService.updateRolePerm(rolePerm);
        }
        map.put("code","101");
        return map;
    }

}
