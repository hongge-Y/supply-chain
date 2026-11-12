package cn.dsscm.service;

import cn.dsscm.pojo.Role;
import java.util.List;

/**
 * 角色业务逻辑接口
 */
public interface RoleService {

    /**
     * 获取所有角色列表
     */
    List<Role> getAllRoles();

    /**
     * 根据ID获取角色
     */
    Role getRoleById(Long id);

    /**
     * 添加角色
     */
    boolean addRole(Role role);

    /**
     * 修改角色
     */
    boolean modifyRole(Role role);

    /**
     * 删除角色
     */
    boolean deleteRole(Long id);
}