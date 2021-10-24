package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.PermissionMapper;
import com.xhy.Mapper.RoleMapper;
import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.service.PermissionService;
import com.xhy.vo.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceimpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Permission> findAllPermission(PermissionVO permissionVO) {
        PageHelper.startPage(permissionVO.getPage(),permissionVO.getLimit());
        return permissionMapper.findAllPermission(permissionVO);

    }

    @Override
    public Integer addPermission(Permission permission) {
        return permissionMapper.addPermission(permission);
    }

    @Override
    public Integer updatePermission(Permission permission) {
        return permissionMapper.updatePermission(permission);
    }

    @Override
    public Integer deletePermission(int id) {
        return  permissionMapper.deletePermission(id);
    }

    @Override
    public List<Permission> findPermission() {
        return permissionMapper.findAllPermission(null);
    }
}
