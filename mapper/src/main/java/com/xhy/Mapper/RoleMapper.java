package com.xhy.Mapper;

import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.vo.RoleVO;

import java.util.List;


public interface RoleMapper {

    /*
    * 查询所有角色
    * */
    List<Role> findAllRole(RoleVO roleVO);

    /*
    * 增加角色
    * */
    Integer addRole(Role role);

    /*
    * 修改角色
    * */
    Integer updateRole(Role role);

    /**
     * 删除角色
     * */
    Integer deleteRole(int roleId);

    /**
     * 获取角色权限
     * */
    List<RolePerm> getRolePerm(int roleId);


    /*
     * 添加角色权限
     * */

    Boolean addRolePerm(RolePerm rolePerm);

    /*
     * 删除角色权限
     * */

    Boolean deleteRolePerm(int roleid);

}
