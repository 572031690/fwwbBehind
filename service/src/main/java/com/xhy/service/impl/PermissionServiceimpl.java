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
    public String addPermission(Permission permission) {
         permissionMapper.addPermission(permission);
         return null;
    }

    @Override
    public String updatePermission(Permission permission) {
        permissionMapper.updatePermission(permission);
        return null;
    }

    @Override
    public String deletePermission(int id) {
        permissionMapper.deletePermission(id);
        return null;
    }

    @Override
    public List<Permission> findPermission() {
        return permissionMapper.findAllPermission(null);
    }
}
