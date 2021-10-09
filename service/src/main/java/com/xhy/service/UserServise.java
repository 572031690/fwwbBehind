package com.xhy.service;

import com.xhy.domain.User;
import com.xhy.domain.UserRole;
import com.xhy.vo.UserVO;

import java.util.List;
import java.util.Set;

public interface UserServise {

    List<User> findalluser(UserVO userVO);


    Boolean findbyname(String username);

    User findbyid(int userid);

    List<Integer> getRoleIdByName(String username);

    Integer logon(User user);

    Integer addUser(User user);

    Integer updataUser(User user);

    Integer deleteUser(int userid);

    User findUser(String username);

    /**
     * 查询当前帐号所有角色
     */
    Set<String> findRoleByUserName(String username);

    /**
     * 查询当前帐号所有权限
     */
    Set<String> findPermissionByUserName(String username);

    /**
     * @Desctiption:添加管理员用户和角色关联
     * */

    Boolean addUserRole(UserRole userRole);

    /**
     * 查询当前帐号所有权限
     */
    Boolean updateUserRole(UserRole userRole);


    /**
     *查询角色用户关联
     * */

    List<UserRole> findUserRole(int roleId);

    /**
     * 删除当前帐号所有权限
     */
    Boolean deleteUserRole(int userId);


    /**
     * 重置密码
     * */
    Boolean updatePassword(int userid);

    /**
    * 状态修改
    * */

    Boolean updateStatus(int userid);


    List<String> findRoleById(int userId);

    Boolean updateUserPassword(int userid, String oldpassword, String password);
}
