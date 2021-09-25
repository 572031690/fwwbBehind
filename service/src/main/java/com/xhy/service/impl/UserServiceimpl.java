package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.UserMapper;
import com.xhy.Utils.CodeUtil;
import com.xhy.domain.User;
import com.xhy.domain.UserRole;
import com.xhy.service.UserServise;
import org.apache.bcel.classfile.Code;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceimpl implements UserServise {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findalluser(int startNum,int pageSize,User user) {
        PageHelper.startPage(startNum,pageSize);
        return userMapper.findAll(user);
    }



    @Override
    public List<Integer> getRoleIdByName(String username) {
        return userMapper.getRoleIdByName(username);
    }

    @Override
    public Integer logon(User user) {
       return userMapper.addUser(user);
    }

    @Override
    public Integer addUser(User user) {
        CodeUtil codeUtil = new CodeUtil();
        user.setPassword("888888");
        User newUser = codeUtil.CodeHash(user);
        return userMapper.addUser(newUser);
    }

    @Override
    public Integer updataUser(User user) {
        CodeUtil codeUtil = new CodeUtil();
        User newUser = codeUtil.CodeHash(user);
        return userMapper.updateUser(newUser);
    }

    @Override
    public Integer deleteUser(int userid) {
        return userMapper.deleteUser(userid);
    }

    @Override
    public Boolean findbyname(String username) {
        if(userMapper.findbyname(username)==null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User findbyid(int userid) {
        return userMapper.findbyid(userid);
    }

    @Override
    public User findUser(String username) {
        return userMapper.findUser(username);
    }

    @Override
    public Set<String> findRoleByUserName(String username) {
        User user = userMapper.findUser(username);
        if(user == null){
            return Collections.EMPTY_SET;
        }
        List<String> allroles = userMapper.findRoleById(user.getUserid());
        Set<String>  allsetRoles = new HashSet<>();
        allsetRoles.addAll(allroles);
        return allsetRoles;
    }

    @Override
    public Set<String> findPermissionByUserName(String username) {
        User user = userMapper.findUser(username);
        if(user == null){
            return Collections.EMPTY_SET;
        }
        List<String> allperm = userMapper.findPermissionById(user.getUserid());
        Set<String>  allsetperm = new HashSet<>();
        allsetperm.addAll(allperm);
        return allsetperm;
    }

    @Override
    public Boolean addUserRole(UserRole userRole) {
        return userMapper.addUserRole(userRole);
    }

    @Override
    public Boolean updateUserRole(UserRole userRole) {

        userMapper.deleteUserRole(userRole.getUserId());
        return userMapper.updateUserRole(userRole);
    }

    @Override
    public Boolean deleteUserRole(int userId) {
        return userMapper.deleteUserRole(userId);
    }

    @Override
    public Boolean updatePassword(int userid) {
        CodeUtil codeUtil = new CodeUtil();
        User user = userMapper.findbyid(userid);
        user.setPassword("88888888");
        User newUser = codeUtil.CodeHash(user);
        Integer status = userMapper.updatePassword(newUser);
        if (status!=0){
            return true;
        }
        else
            return false;
    }

    @Override
    public Boolean updateStatus(int userid) {
        User user = userMapper.findbyid(userid);
        if(user.getIsDisabled()==0){
            user.setIsDisabled(1);
            Integer status = userMapper.updateStatus(user);
            if(status!=0){
                return true;
            }
            else
                return false;
        }
        else if(user.getIsDisabled()==1)
        {
            user.setIsDisabled(0);
            Integer status = userMapper.updateStatus(user);
            if(status!=0){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }


}
