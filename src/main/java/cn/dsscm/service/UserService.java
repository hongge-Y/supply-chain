package cn.dsscm.service;

import cn.dsscm.pojo.User;
import java.util.List;

/**
 * 用户业务逻辑接口
 */
public interface UserService {

    /**
     * 用户登录验证
     * @param userCode 用户编码
     * @param userPassword 密码
     * @return 登录成功返回用户对象，失败返回 null
     */
    User login(String userCode, String userPassword);

    /**
     * 分页查询用户列表（可按姓名、角色筛选）
     * @param userName 真实姓名（模糊查询）
     * @param roleId   角色ID
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @return 用户列表
     */
    List<User> getUserList(String userName, Integer roleId, int pageIndex, int pageSize);

    /**
     * 查询符合条件的用户总数
     */
    int getUserCount(String userName, Integer roleId);

    /**
     * 添加用户
     */
    boolean addUser(User user);

    /**
     * 修改用户
     */
    boolean modifyUser(User user);

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);

    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);
    /**
     * 根据用户编码查询用户（用于注册时查重）
     */
    User getUserByCode(String userCode);

    /**
     * 激活或禁用用户
     */
    boolean updateUserStatus(Long id, Integer status);

    /**
     * 获取待审核用户列表
     */
    List<User> getPendingUsers();

    /**
     * 获取待审核用户数量
     */
    int getPendingCount();

    /**
     * 提交审批（草稿→待审批）
     */
    boolean submitForApproval(Long id);
}