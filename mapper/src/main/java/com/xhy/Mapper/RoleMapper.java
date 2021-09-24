package com.xhy.Mapper;

import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.domain.UserRole;

import java.util.List;


public interface RoleMapper {

    /*
    * 查询所有角色
    * */
    List<Role> findAllRole(String rolename);

    /*
    * 增加角色
    * */
    void addRole(Role role);

    /*
    * 修改角色
    * */
    void updateRole(Role role);

    /*
    * 删除角色
    * */
    void deleteRole(int roleId);



    /*
     * 添加角色权限
     * */

    Boolean addRolePerm(RolePerm rolePerm);

    /*
     * 修改角色权限
     * */

    Boolean updateRolePerm(RolePerm rolePerm);

    /*
     * 删除角色权限
     * */

    Boolean deleteRolePerm(int roleid);

}
