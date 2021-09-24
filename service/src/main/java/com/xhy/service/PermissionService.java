package com.xhy.service;

import com.xhy.domain.Permission;
import com.xhy.domain.Role;

import java.util.List;

public interface PermissionService {

    /*
     * 查询所有权限
     * */

    List<Permission> findAllPermission(int startNum, int pageSize, String name);

    /*
     * 添加权限
     * */
    String addPermission(Permission permission);

    /*
     * 修改权限
     * */
    String updatePermission(Permission permission);

    /*
     * 删除权限
     * */
    String deletePermission(int id);

    List<Permission> findPermission();




}
