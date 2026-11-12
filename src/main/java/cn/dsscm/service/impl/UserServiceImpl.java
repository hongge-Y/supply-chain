package cn.dsscm.service.impl;

import cn.dsscm.dao.UserMapper;
import cn.dsscm.pojo.User;
import cn.dsscm.service.UserService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户业务逻辑实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userCode, String userPassword) {
        return userMapper.selectByCodeAndPwd(userCode, userPassword);
    }

    @Override
    public List<User> getUserList(String userName, Integer roleId, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return userMapper.selectByPage(userName, roleId, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getUserCount(String userName, Integer roleId) {
        return userMapper.selectCount(userName, roleId);
    }

    @Override
    public boolean addUser(User user) {
        user.setCreationDate(new Date());
        return userMapper.insertUser(user) > 0;
    }

    @Override
    public boolean modifyUser(User user) {
        user.setModifyDate(new Date());
        return userMapper.updateUser(user) > 0;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteUser(id) > 0;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
    @Override
    public User getUserByCode(String userCode) {
        return userMapper.selectByUserCode(userCode);
    }

    @Override
    public boolean updateUserStatus(Long id, Integer status) {
        return userMapper.updateUserStatus(id, status) > 0;
    }

    @Override
    public List<User> getPendingUsers() {
        return userMapper.selectPendingUsers();
    }

    @Override
    public int getPendingCount() {
        return userMapper.selectPendingCount();
    }

    @Override
    public boolean submitForApproval(Long id) {
        // 注意：User 表没有独立的 approvalStatus 字段，
        // 复用 status 字段：3 表示"已提交审批"（相当于其他模块的 approvalStatus=1）
        return userMapper.updateUserStatus(id, 3) > 0;
    }
}