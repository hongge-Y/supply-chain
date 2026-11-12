package cn.dsscm.dao;

import cn.dsscm.pojo.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户表数据访问接口
 */
public interface UserMapper {

    /**
     * 根据用户编码和密码查询用户（用于登录验证）
     * @param userCode 用户编码
     * @param userPassword 用户密码
     * @return 匹配的用户对象，若无则返回 null
     */
    User selectByCodeAndPwd(@Param("userCode") String userCode,
                            @Param("userPassword") String userPassword);

    /**
     * 条件分页查询用户列表
     * @param userName 按真实姓名模糊查询（可选）
     * @param roleId   按角色ID精确查询（可选）
     * @param start    分页起始行（从0开始）
     * @param size     每页记录数
     * @return 用户列表
     */
    List<User> selectByPage(@Param("userName") String userName,
                            @Param("roleId") Integer roleId,
                            @Param("start") int start,
                            @Param("size") int size);

    /**
     * 统计满足条件的用户总数
     * @param userName 真实姓名（模糊）
     * @param roleId   角色ID
     * @return 总记录数
     */
    int selectCount(@Param("userName") String userName,
                    @Param("roleId") Integer roleId);

    /**
     * 新增用户
     * @param user 用户对象
     * @return 受影响行数
     */
    int insertUser(User user);

    /**
     * 修改用户（动态更新非空字段）
     * @param user 用户对象（至少包含id）
     * @return 受影响行数
     */
    int updateUser(User user);

    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 受影响行数
     */
    int deleteUser(@Param("id") Long id);

    /**
     * 根据ID查询单个用户
     * @param id 用户ID
     * @return 用户对象
     */
    User selectById(@Param("id") Long id);
    User selectByUserCode(@Param("userCode") String userCode);

    /**
     * 更新用户状态（审核激活 / 禁用）
     * @param id 用户ID
     * @param status 目标状态：0=待审核 1=正常 2=已禁用
     */
    int updateUserStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询所有待审核用户
     */
    List<User> selectPendingUsers();

    /**
     * 统计待审核用户数量
     */
    int selectPendingCount();
}