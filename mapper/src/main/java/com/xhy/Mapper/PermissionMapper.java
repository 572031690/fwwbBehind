package com.xhy.Mapper;

import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.vo.PermissionVO;

import java.util.List;

public interface PermissionMapper {
    /*
     * 查询所有权限
     * */
    List<Permission> findAllPermission(PermissionVO permissionVO);

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

    /**
     * 修改权限状态
     * */

    Integer updatePermissionStatus(Permission permission);

}
