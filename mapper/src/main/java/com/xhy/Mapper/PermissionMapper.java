package com.xhy.Mapper;

import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;

import java.util.List;

public interface PermissionMapper {
    /*
     * 查询所有权限
     * */
    List<Permission> findAllPermission(String name);

    /*
     * 增加权限
     * */
    Integer addPermission(Permission permission);

    /*
     * 修改权限
     * */
    Integer updatePermission(Permission permission);

    /*
     * 删除权限
     * */
    Integer deletePermission(int id);

}
