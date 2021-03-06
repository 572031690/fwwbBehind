package com.xhy.Mapper;

import com.xhy.domain.User;
import com.xhy.domain.UserRole;

import java.util.List;

public interface UserMapper {

    List<User> findAll(String searchName);

    User findbyid(int userid);

    User findUser(String username);

    /*获取用户角色id*/
    List<Integer> getRoleIdByName(String username);

    Integer addUser(User user);

    Integer updateUser(User user);
    /*
    * 逻辑删除
    * */
    Integer updateDisplayed(int userid);

    User findbyname(String username);
    /*
     * 查询当前用户的角色
     * */
    List<String> findRoleById(int userId);
    /*
    * 查询当前用户的权限
    * */
    List<String> findPermissionById(int userId);

    /*
    * 查询当前用户权限的id
    * */
    List<Integer> findPermissionId(int userId);


    /*
    * 添加用户角色关联
    * */

    boolean addUserRole(UserRole userRole);

    /*
    *
    *修改用户角色关联
    * */
    boolean updateUserRole(UserRole userRole);
    /*
    * 删除用户角色关联
    * */
    boolean deleteUserRole(int userId);

    /*
    * 查询用户角色表
    * */

    List<UserRole> findUserRole(int roleId);


    /**
     *重置用户密码
     * */
    Integer updatePassword(User user);

    /**
     * 修改用户状态
     * */

    Integer updateStatus(User user);





}
