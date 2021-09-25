package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.PermissionMapper;
import com.xhy.domain.Permission;
import com.xhy.domain.Role;
import com.xhy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceimpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAllPermission(int startNum, int pageSize, String name) {
        PageHelper.startPage(startNum,pageSize);
        return permissionMapper.findAllPermission(name);

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
