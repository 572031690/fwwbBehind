package com.xhy.service;

import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.domain.UserRole;
import com.xhy.vo.RoleVO;

import java.util.List;

public interface RoleService {

    /*
    * 查询所有角色
    * */

    List<Role> findAllRole(RoleVO roleVO);

    /*
    * 添加角色
    * */
    Integer addRole(Role role);

    /*
     * 修改角色
     * */
    Integer updateRole(Role role);

    /*
     * 删除角色
     * */
    Integer deleteRole(int roleId);

    List<Role> findRole();

    /**
     * 修改角色状态
     * */

    Integer updateRoleStatus(Role role);

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
