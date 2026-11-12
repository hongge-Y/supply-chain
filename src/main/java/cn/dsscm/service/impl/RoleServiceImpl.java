package cn.dsscm.service.impl;

import cn.dsscm.dao.RoleMapper;
import cn.dsscm.pojo.Role;
import cn.dsscm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色业务逻辑实现类
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public boolean addRole(Role role) {
        role.setCreationDate(new Date());
        return roleMapper.insertRole(role) > 0;
    }

    @Override
    public boolean modifyRole(Role role) {
        role.setModifyDate(new Date());
        return roleMapper.updateRole(role) > 0;
    }

    @Override
    public boolean deleteRole(Long id) {
        return roleMapper.deleteRole(id) > 0;
    }
}