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
    void addPermission(Permission permission);

    /*
     * 修改权限
     * */
    void updatePermission(Permission permission);

    /*
     * 删除权限
     * */
    void deletePermission(int id);

}
