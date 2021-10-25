package com.xhy.service;

import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.vo.PermissionVO;

import java.util.List;

public interface PermissionService {

    /*
     * 查询所有权限
     * */

    List<Permission> findAllPermission(PermissionVO permissionVO);

    /*
     * 添加权限
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

    List<Permission> findPermission();


    /**
     * 修改权限状态
     * */

    Integer updatePermissionStatus(Permission permission);




}
