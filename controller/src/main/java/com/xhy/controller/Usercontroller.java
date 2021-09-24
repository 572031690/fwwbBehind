package com.xhy.controller;

import com.github.pagehelper.PageInfo;
import com.xhy.Utils.CodeUtil;
import com.xhy.domain.Role;
import com.xhy.domain.User;
import com.xhy.domain.UserRole;
import com.xhy.service.RoleService;
import com.xhy.service.UserServise;
import com.xhy.vo.AdminUserVO;
import com.xhy.vo.UserRoleVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@Controller

@RequestMapping(value = "/web")
public class Usercontroller {

    @Autowired
    UserServise userServise;
    @Autowired
    RoleService roleService;


    //    权限登录
    @RequestMapping(value = "/shirologin", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> login(AdminUserVO adminUserVO) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userServise.findUser(adminUserVO.getUsername());
        try {
            if(adminUserVO!=null) {
                //创建主体
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(adminUserVO.getUsername(), adminUserVO.getPassword());
                subject.login(usernamePasswordToken);
                if(adminUserVO.isRemember()) {
                    //shiro会将认证的信息写入到本地的cookie中，下次访问，记取bendicookie到服务器
                    usernamePasswordToken.setRememberMe(true);
                }
            }

            map.put("code", "101");
            map.put("user", user);

        } catch (UnknownAccountException ex) {
            System.out.println("输入的账号不存在");
            map.put("code","102");
            map.put("error","输入账号不存在");
        } catch (IncompatibleClassChangeError ex) {
            System.out.println("输入用户名密码不正确，请重新输入");
            map.put("code","102");
            map.put("error","输入用户名密码不正确，请重新输入");
        }

        return map;
    }


    /**
     * 用户未登录
     * */

    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Map<String,Object> unauth(){
        Map<String,Object> map = new HashMap<>();

        map.put("code","406");
        map.put("error","用户未登录");
        return map;
    }


    /**
     * 用户未授权
     * */

    @RequestMapping(value = "/unauthorized")
    @ResponseBody
    public Map<String,Object> unauthorized(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","403");
        map.put("error","没有权限");
        return map;
    }

    /**
     * 用户退出登录
     * */
    @GetMapping("/logout")
    @ResponseBody
    public Map<String,Object> logout(){
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        map.put("code","407");
        map.put("error","退出登录");
        return map;
    }

    /**
     * 账号被禁用
     * */
    @GetMapping("/stopAccount")
    @ResponseBody
    public Map<String,Object>  stopAccount(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","408");
        map.put("error","账户已被禁用");
        return map;
    }

    /**
     * 账号不存在或输入的账号不正确
     * */
    @GetMapping("/unknowAccount")
    @ResponseBody
    public Map<String,Object>  unknowAccount(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","409");
        map.put("error","账号不存在或输入的账号不正确");
        return map;
    }

    /**
     * 您输入的密码不正确
     * */
    @GetMapping("/Incorrect")
    @ResponseBody
    public Map<String,Object>  Incorrect(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","501");
        map.put("error","您输入的密码不正确");
        return map;
    }


    /**
    * 用户注册
    * */
    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> logon(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        if (user.getUsername() == null) {
            map.put("code", "102");
            return map;
        } else if (user.getPassword() == null) {
            map.put("code", "102");
            return map;
        } else if (user.getTelNum() == null) {
            map.put("code", "102");
            return map;
        } else if (user.getEmployeeid() == null) {
            map.put("code", "102");
            return map;
        } else if (userServise.findbyname(user.getUsername())) {
            CodeUtil codeUtil = new CodeUtil();
            codeUtil.CodeHash(user);
            Integer status = userServise.logon(user);
            if(status!=null) {
                map.put("code", "101");
                return map;
            }
            else {
                map.put("code","102");
                return map;
            }
        } else {
            map.put("code", "102");
            return map;
        }
    }


    @RequiresPermissions("admin:userlist")
    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> ListUser(Integer page, Integer limit,User user) {

        Map<String, Object> map = new HashMap<String, Object>();
        List<User> list1 = userServise.findalluser(0, 0, user);
        PageInfo pageInfo1 = new PageInfo(list1);
        int count = pageInfo1.getSize();
        map.put("count", count);
        List<User> list2 = userServise.findalluser(page, limit, user);
        PageInfo pageInfo2 = new PageInfo(list2);
        int pageNum = pageInfo2.getPageNum();
        map.put("list", list2);
        map.put("page", pageNum);
        return map;
    }

    @RequiresPermissions("admin:addUser")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        CodeUtil codeUtil = new CodeUtil();
        codeUtil.CodeHash(user);
        Integer status= userServise.addUser(user);
        if(status!=null){
            map.put("code", "101");
            return map;
        }
        else
        {
            map.put("code","102");
            return map;
        }
    }


    @RequiresPermissions("admin:updateUser")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        CodeUtil codeUtil = new CodeUtil();
        codeUtil.CodeHash(user);
        Integer status= userServise.updataUser(user);
        if(status!=null){
            map.put("code", "101");
            return map;
        }
        else
        {
            map.put("code","102");
            return map;
        }
    }

    @RequiresPermissions("admin:deleteUser")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> deleteUser(int userid) {
        Integer status = userServise.deleteUser(userid);
        userServise.deleteUserRole(userid);
        Map<String, Object> map = new HashMap<String, Object>();
        if(status!=null){
            map.put("code", "101");
            return map;
        }
        else
        {
            map.put("code","102");
            return map;
        }
    }


    /**
     * 添加用户角色关联
     * */


    @RequiresPermissions("admin:addUserRole")
    @PostMapping("/addUserRole")
    @ResponseBody
    public Map<String,Object> addUserRole(@RequestBody UserRoleVO userRole){
        Map<String,Object> map = new HashMap<>();
        List<Integer> roleIds = userRole.getRoleId();
        UserRole userRole1 = new UserRole();
        userRole1.setUserId(userRole.getUserId());
        for(Integer roleId: roleIds){
            userRole1.setRoleId(roleId);
            userServise.addUserRole(userRole1);
        }
        map.put("code","101");
        return map;
    }


    /**
     * 获取当前用户的所有角色
    * */

    @RequiresPermissions("admin:getUserRole")
    @GetMapping("/getUserRole")
    @ResponseBody
    public Map<String,Object> getUserRole(String username){
        Map<String,Object> map=new HashMap<>();
        List<Role> role = roleService.findRole();
        Set<String> roleByUserName = userServise.findRoleByUserName(username);
        if(role != null){
            map.put("code","101");
            map.put("list",roleByUserName);
            map.put("rolelist",role);
        }
        else{
            map.put("code","102");
        }

        return map;
    }

    /**
     * 修改用户角色关联
     * */
    @RequiresPermissions("admin:updateRole")
    @PostMapping("/updateUserRole")
    @ResponseBody
    public Map<String,Object> updateUserRole(@RequestBody UserRoleVO userRole){
        Map<String,Object> map = new HashMap<>();
        List<Integer> roleIds = userRole.getRoleId();
        UserRole userRole1 = new UserRole();
        userRole1.setUserId(userRole.getUserId());
        for(Integer roleId: roleIds){

            userRole1.setRoleId(roleId);
            userServise.updateUserRole(userRole1);
        }
        map.put("code","101");
        return map;
    }

    /*
    * 查找
    * */


    /**
     *重置密码
     * */
    @RequiresPermissions("admin:invertPassword")
    @ResponseBody
    @GetMapping("/invertPassword")
    public Map<String,Object> updateUserRole(Integer userid){
        Map<java.lang.String, java.lang.Object> map = new HashMap<>();
        Boolean aBoolean= userServise.updatePassword(userid);
        if(aBoolean = true){
            map.put("code","101");
        }else
        {
            map.put("code","102");
        }
        return map;
    }


    /**
     * 状态修改
     * */

    @RequiresPermissions("admin:updateStatus")
    @ResponseBody
    @GetMapping("/updateStatus")
    public Map<String,Object> updateStatus(Integer userid){
        Map<java.lang.String, java.lang.Object> map = new HashMap<>();
        Boolean aBoolean= userServise.updateStatus(userid);
        if(aBoolean = true){
            map.put("code","101");
        }
        else
        {
            map.put("code","102");
        }
        return map;
    }
}
