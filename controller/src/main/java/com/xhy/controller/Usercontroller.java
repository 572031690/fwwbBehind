package com.xhy.controller;

import com.github.pagehelper.PageInfo;
import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.domain.User;
import com.xhy.domain.UserRole;
import com.xhy.service.PermissionService;
import com.xhy.service.RoleService;
import com.xhy.service.UserServise;
import com.xhy.vo.AdminUserVO;
import com.xhy.vo.UpdatePasswordVO;
import com.xhy.vo.UserRoleVO;
import com.xhy.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "/web")
public class Usercontroller {

    @Autowired
    UserServise userServise;
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    /**
     * 获取cookie
     * */


    //    权限登录

    @RequestMapping(value = "/shirologin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody AdminUserVO adminUserVO) {
        Map<String, Object> map = new HashMap<String, Object>();
        Subject subject=null;
        try {
            if(adminUserVO!=null) {
                //创建主体
                subject = SecurityUtils.getSubject();
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(adminUserVO.getUsername(), adminUserVO.getPassword());
                subject.login(usernamePasswordToken);
                if(adminUserVO.isRemember()) {
                    //shiro会将认证的信息写入到本地的cookie中，下次访问，记取bendicookie到服务器
                    usernamePasswordToken.setRememberMe(true);
                }
            }
            User user = userServise.findUser(adminUserVO.getUsername());
            List<Integer> roleIdByName = userServise.getRoleIdByName(adminUserVO.getUsername());

            if (roleIdByName.size() != 0) {
                for (Integer roleId : roleIdByName) {
                    if(user.getRoleId()==null){
                        user.setRoleId(Collections.singletonList(roleId));
                    }
                    else{
                        List<Integer> userRole = new ArrayList<>();
                        for(Integer i : user.getRoleId()){
                            userRole.add(i);
                        }
                        userRole.add(roleId);
                        user.setRoleId(userRole);
                    }
                }
            } else {
                user.setRoleId(Collections.singletonList(0));
            }
            map.put("code", "101");
            map.put("sessionId",subject.getSession().getId()); //回传sessionId
            map.put("user", user);
            List<Permission> permission = permissionService.findPermission();
            map.put("permission",permission);
            Set<String> permissionName = userServise.findPermissionByUserName(adminUserVO.getUsername());
            map.put("permissionName",permissionName);
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
        map.put("code","102");
        map.put("error","没有权限");
        return map;
    }


    /**
     * 账号被禁用
     * */
    @RequestMapping("/stopAccount")
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
    @RequestMapping ("/unknowAccount")
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
    @PostMapping("/Incorrect")
    @ResponseBody
    public Map<String,Object>  Incorrect(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","501");
        map.put("error","您输入的密码不正确");
        return map;
    }

    /**
     * 用户登录成功
     * */

    @PostMapping(value = "/success")
    @ResponseBody
    public Map<String,Object> success(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","101");
        map.put("error","登陆成功");
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
        } else if (!userServise.findbyname(user.getUsername())) {
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


    @ApiOperation(value = "获取全部用户列表", notes = "获取全部用户列表", httpMethod = "GET", response = User.class)
    @RequiresPermissions("admin:userlist")
    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> ListUser(UserVO uservo) {
        //参数定义
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList = new ArrayList<>();

        //获取page&list
        List<User> list2 = userServise.findalluser(uservo);
        PageInfo pageInfo2 = new PageInfo(list2);
        long total = pageInfo2.getTotal();
        int pageNum = pageInfo2.getPageNum();
        for( User user1:list2){
                List<Integer> roleid = userServise.getRoleIdByName(user1.getUsername());
                if (roleid.size() != 0) {
                    for (Integer roleId : roleid) {
                        if (user1.getRoleId() == null) {
                            user1.setRoleId(Collections.singletonList(roleId));
                            userList.add(user1);
                        }
                        else {
                            List<Integer> userRole = new ArrayList<>();
                            for (Integer i : user1.getRoleId()) {
                                userRole.add(i);
                            }
                            userRole.add(roleId);
                            user1.setRoleId(userRole);
                        }
                    }
                } else {
                    user1.setRoleId(Collections.singletonList(0));
                    userList.add(user1);
                }
        }
        List<User>  users = new ArrayList<>();
        for (User user : userList){
            for (Integer integer : user.getRoleId()) {
                if(integer!=10000){
                    users.add(user);
                }
            }
        }
        map.put("list",users);
        map.put("page", pageNum);
        map.put("count", total);
        //查询筛选状态
        if(uservo.getSelectName()!=null && !uservo.getSelectName().isEmpty()){
            List<User> selectUserList = new ArrayList<>();
            for(User userSelect : userList){
                for(Integer selectRoleId : userSelect.getRoleId()) {
                     String roleid = String.valueOf(selectRoleId);
                    if (roleid.equals(uservo.getSelectName())) {
                        selectUserList.add(userSelect);
                    }
                }
            }
            map.put("list",selectUserList);
        }


        return map;
    }

    @RequiresPermissions("admin:addUser")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        /*将员工号统一化处理*/
        StringBuilder stringBuilderuser = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String[] split = format.split("-");
        String s = stringBuilderuser.append("ZNKC" + split[0]+split[1]+split[2]+user.getEmployeeid()).toString();
        user.setEmployeeid(s);
        Integer status= userServise.addUser(user);
        if(status!=0){
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
        /*将员工号统一化处理*/
        StringBuilder stringBuilderuser = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String[] split = format.split("-");
        String s = stringBuilderuser.append("ZNKC" + split[0]+split[1]+split[2]+user.getEmployeeid()).toString();
        user.setEmployeeid(s);
        Integer status= userServise.updataUser(user);
        if(status!=0){
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
        Boolean aBoolean = userServise.deleteUserRole(userid);
        Map<String, Object> map = new HashMap<String, Object>();
        if((status != 0 & aBoolean) == true){
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


    @RequiresPermissions("admin:addUerRole")
    @PostMapping("/addUserRole")
    @ResponseBody
    public Map<String,Object> addUserRole(@RequestBody UserRoleVO userRole){
        Map<String,Object> map = new HashMap<>();
        List<Integer> roleIds = userRole.getRoleId();
        UserRole userRole1 = new UserRole();
        userRole1.setUserId(userRole.getUserid());
        List<String> roleById = userServise.findRoleById(userRole.getUserid());
        if(!roleById.isEmpty()){
           userServise.deleteUserRole(userRole.getUserid());
        }
        for(Integer roleId: roleIds){
            userRole1.setRoleId(roleId);
            userServise.addUserRole(userRole1);
        }
        map.put("code","101");
        return map;
    }


    /**
     * 获取当前用户的所有角色和角色列表
    * */

//    @RequiresPermissions("admin:getUserRole")
    @GetMapping("/getUserRole")
    @ResponseBody
    public Map<String,Object> getUserRole(String username){
        Map<String,Object> map=new HashMap<>();
        List<Role> role = roleService.findRole();
        Set<String> roleList = userServise.findRoleByUserName(username);
        if(role != null){
            map.put("code","101");
            map.put("userRoleList",roleList);
            map.put("roleList",role);
        }
        else{
            map.put("code","102");
        }

        return map;
    }

    /**
     *重置密码
     * */
    @RequiresPermissions("admin:invertPassword")
    @ResponseBody
    @GetMapping("/invertPassword")
    public Map<String,Object> updateUserRole(Integer userid){
        Map<java.lang.String, java.lang.Object> map = new HashMap<>();
        Boolean aBoolean= userServise.updatePassword(userid);
        if(aBoolean == true){
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
        if(aBoolean == true){
            map.put("code","101");
        }
        else
        {
            map.put("code","102");
        }
        return map;
    }


    @RequestMapping(value = "/updateUserPassword" , method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateUserPassword(@RequestBody UpdatePasswordVO updatePasswordVO){
        Map<String,Object> map =new HashMap<>();
        Boolean aBoolean = userServise.updateUserPassword(updatePasswordVO.getUserid(),updatePasswordVO.getOldpassword(),updatePasswordVO.getPassword());
        if(aBoolean == true){
            map.put("code","101");
        }
        else{
            map.put("code","102");
        }

        return map;
    }
}
