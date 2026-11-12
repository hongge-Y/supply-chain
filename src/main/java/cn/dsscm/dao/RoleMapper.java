package cn.dsscm.dao;

import cn.dsscm.pojo.Role;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色表数据访问接口
 */
public interface RoleMapper {

    /**
     * 查询所有角色
     */
    List<Role> selectAll();

    /**
     * 根据ID查询角色
     */
    Role selectById(@Param("id") Long id);

    /**
     * 添加角色
     */
    int insertRole(Role role);

    /**
     * 更新角色
     */
    int updateRole(Role role);

    /**
     * 删除角色
     */
    int deleteRole(@Param("id") Long id);
}