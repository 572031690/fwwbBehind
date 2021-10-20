package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.PermissionMapper;
import com.xhy.Mapper.RoleMapper;
import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceimpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> findAllRole(int startNum, int pageSize,String rolename) {
        PageHelper.startPage(startNum,pageSize);
        return roleMapper.findAllRole(rolename);
    }

    @Override
    public String addRole(Role role) {
        roleMapper.addRole(role);
        return null;
    }

    @Override
    public String updateRole(Role role) {
        roleMapper.updateRole(role);

        return null;
    }

    @Override
    public String deleteRole(int roleId) {
        roleMapper.deleteRole(roleId);
        return null;
    }

    @Override
    public List<Role> findRole() {
        return roleMapper.findAllRole(null);
    }

    @Override
    public List<RolePerm> getRolePerm(int roleId) {
        return roleMapper.getRolePerm(roleId);
    }

    @Override
    public Boolean addRolePerm(RolePerm rolePerm) {
        return roleMapper.addRolePerm(rolePerm);
    }


    @Override
    public Boolean deleteRolePerm(int roleId) {
        return roleMapper.deleteRolePerm(roleId);
    }


}
