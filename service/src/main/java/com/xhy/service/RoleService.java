package com.xhy.service;

import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.domain.UserRole;

import java.util.List;

public interface RoleService {

    /*
    * 查询所有角色
    * */

    List<Role> findAllRole(int startNum,int pageSize,String rolename);

    /*
    * 添加角色
    * */
    String addRole(Role role);

    /*
     * 修改角色
     * */
    String updateRole(Role role);

    /*
     * 删除角色
     * */
    String deleteRole(int roleId);

    List<Role> findRole();

    /**
     * 获取角色权限
     * */
    List<RolePerm> getRolePerm(int roleId);

    /*
    * 添加角色权限的关联
    * */

    Boolean addRolePerm (RolePerm rolePerm);

    /**
     * 删除角色权限关联
     * */

    Boolean deleteRolePerm(int roleId);

}
