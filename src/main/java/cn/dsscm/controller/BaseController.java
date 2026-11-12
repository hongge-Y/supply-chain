package cn.dsscm.controller;

import cn.dsscm.pojo.User;
import javax.servlet.http.HttpSession;

/**
 * 控制器基类
 * 提供各 Controller 通用的权限校验方法，消除重复代码。
 * 各业务 Controller 继承此类后，可直接使用 hasPermission() 系列方法。
 */
public class BaseController {

    /** 角色常量 — 警告：这些值必须与 tb_role 表 seed 数据一致 */
    protected static final int ROLE_ADMIN = 1;       // 系统管理员
    protected static final int ROLE_MANAGER = 2;      // 经理
    protected static final int ROLE_EMPLOYEE = 3;     // 新注册/未分配岗位
    protected static final int ROLE_PERSONNEL = 4;    // 人事部
    protected static final int ROLE_PURCHASE = 5;     // 采购部
    protected static final int ROLE_MATERIAL = 6;     // 物资部
    protected static final int ROLE_SALES = 7;        // 销售部
    // TODO: 角色 ID 硬编码在这里，若 tb_role 表 seed 数据变更则全部权限校验失效。
    //       建议改为通过 roleCode（如 "DSSCM_ADMIN"）进行权限判断，与 header.jsp 保持一致。

    // ========== 菜单权限 ==========

    /**
     * 是否有权访问用户管理模块（人事部、经理、管理员）
     */
    protected boolean canAccessUser(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER, ROLE_PERSONNEL);
    }

    /**
     * 是否有权访问供应商管理模块（采购部、经理、管理员）
     */
    protected boolean canAccessProvider(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER, ROLE_PURCHASE);
    }

    /**
     * 是否有权访问采购订单模块（采购部、经理、管理员）
     */
    protected boolean canAccessBill(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER, ROLE_PURCHASE);
    }

    /**
     * 是否有权访问商品管理模块（物资部、经理、管理员）
     */
    protected boolean canAccessProduct(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER, ROLE_MATERIAL);
    }

    /**
     * 是否有权访问销售订单模块（销售部、经理、管理员）
     */
    protected boolean canAccessOrder(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER, ROLE_SALES);
    }

    /**
     * 是否有权访问新闻管理模块（所有登录用户均可查看）
     */
    protected boolean canAccessNews(HttpSession session) {
        return true;
    }

    /**
     * 是否有新闻发文权限（仅销售部、经理、管理员）
     */
    protected boolean canPublishNews(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER, ROLE_SALES);
    }

    /**
     * 经营统计所有人可见
     */
    protected boolean canAccessStatistics(HttpSession session) {
        return true;
    }

    /**
     * 是否有审批权限（管理员、经理）
     */
    protected boolean canApprove(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER);
    }

    // ========== 操作权限 ==========

    /**
     * 是否为管理员/经理（拥有不受限的操作权限）
     */
    protected boolean isAdminOrManager(HttpSession session) {
        return hasAnyRole(session, ROLE_ADMIN, ROLE_MANAGER);
    }

    /**
     * 是否对指定单据有编辑权限
     * 经理/管理员：任何状态均可编辑
     * 普通员工：只能编辑自己创建的草稿
     */
    protected boolean canEdit(HttpSession session, Long recordUserId, Integer approvalStatus) {
        if (isAdminOrManager(session)) return true;
        User user = getLoginUser(session);
        return user != null && user.getId().equals(recordUserId) && approvalStatus != null && approvalStatus == 0;
    }

    /**
     * 是否对指定单据有删除权限
     * 经理/管理员：可直接删除
     * 普通员工：只能删除自己创建的草稿，已审批数据需提交删除申请
     */
    protected boolean canDelete(HttpSession session, Long recordUserId, Integer approvalStatus) {
        if (isAdminOrManager(session)) return true;
        User user = getLoginUser(session);
        return user != null && user.getId().equals(recordUserId) && approvalStatus != null && approvalStatus == 0;
    }

    /**
     * 是否需要通过申请才能修改（非经理/管理员，且数据不是草稿）
     */
    protected boolean needApplyToModify(HttpSession session, Long recordUserId, Integer approvalStatus) {
        if (isAdminOrManager(session)) return false;
        return !(approvalStatus != null && approvalStatus == 0);
    }

    // ========== 统一权限网关 ==========

    /**
     * 统一权限检查入口，将模块名映射为对应的 canAccess 方法
     * 各 Controller 可直接调用 hasDirectPermission(session, "bill") 等
     */
    protected boolean hasDirectPermission(HttpSession session, String module) {
        switch (module) {
            case "bill":     return canAccessBill(session);
            case "provider": return canAccessProvider(session);
            case "product":  return canAccessProduct(session);
            case "order":    return canAccessOrder(session);
            case "news":     return canAccessNews(session);
            case "user":     return canAccessUser(session);
            default:         return false;
        }
    }

    // ========== 工具方法 ==========

    protected User getLoginUser(HttpSession session) {
        return (User) session.getAttribute("loginUser");
    }

    protected Integer getUserRole(HttpSession session) {
        User user = getLoginUser(session);
        return user != null ? user.getUserRole() : null;
    }

    protected boolean hasRole(HttpSession session, int roleId) {
        Integer role = getUserRole(session);
        return role != null && role == roleId;
    }

    protected boolean hasAnyRole(HttpSession session, int... roleIds) {
        Integer userRole = getUserRole(session);
        if (userRole == null) return false;
        for (int r : roleIds) {
            if (userRole == r) return true;
        }
        return false;
    }
}
