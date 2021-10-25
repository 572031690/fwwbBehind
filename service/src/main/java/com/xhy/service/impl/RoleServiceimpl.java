package com.xhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.xhy.Mapper.PermissionMapper;
import com.xhy.Mapper.RoleMapper;
import com.xhy.domain.Role;
import com.xhy.domain.RolePerm;
import com.xhy.service.RoleService;
import com.xhy.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceimpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> findAllRole(RoleVO roleVO) {
        PageHelper.startPage(roleVO.getPage(),roleVO.getLimit());
        return roleMapper.findAllRole(roleVO);
    }

    @Override
    public Integer addRole(Role role) {

        return roleMapper.addRole(role);
    }

    @Override
    public Integer updateRole(Role role) {


        return roleMapper.updateRole(role);
    }

    @Override
    public Integer deleteRole(int roleId) {
        return roleMapper.deleteRole(roleId);
    }

    @Override
    public List<Role> findRole() {
        return roleMapper.findAllRole(null);
    }

    @Override
    public Integer updateRoleStatus(Role role) {
        return roleMapper.updateRoleStatus(role);
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
