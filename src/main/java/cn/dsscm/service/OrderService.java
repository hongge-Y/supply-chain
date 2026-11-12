package cn.dsscm.service;

import cn.dsscm.pojo.Order;
import cn.dsscm.pojo.OrderDetail;
import java.util.List;

/**
 * 销售订单业务逻辑接口
 */
public interface OrderService {

    /**
     * 分页查询销售订单（只返回审批通过的数据）
     */
    List<Order> getOrderList(String userName, Integer status, int pageIndex, int pageSize);

    /**
     * 统计销售订单数量
     */
    int getOrderCount(String userName, Integer status);

    /**
     * 添加订单（同时添加明细）
     */
    boolean addOrder(Order order, List<OrderDetail> details);

    /**
     * 修改订单基本信息（不含明细）
     */
    boolean modifyOrder(Order order);

    /**
     * 删除订单（级联删除明细）
     */
    boolean deleteOrder(Long id);

    /**
     * 根据ID获取订单
     */
    Order getOrderById(Long id);

    /**
     * 根据订单ID获取明细列表
     */
    List<OrderDetail> getOrderDetailsByOrderId(Integer orderId);

    /**
     * 提交审批：草稿→待审批
     */
    boolean submitForApproval(Long id);

    /**
     * 审批通过：待审批→通过
     */
    boolean approve(Long id);

    /**
     * 审批驳回：待审批→驳回
     */
    boolean reject(Long id);

    /**
     * 待审批数量
     */
    int getPendingApprovalCount();

    /**
     * 分页查询当前用户能看到的销售订单列表（自己的草稿/驳回 + 所有审批通过的）
     */
    List<Order> getMyOrderList(Long userId, String userName, Integer status, int pageIndex, int pageSize);

    /**
     * 统计当前用户能看到的销售订单数量
     */
    int getMyOrderCount(Long userId, String userName, Integer status);

    /**
     * 查询所有待审批的销售订单列表
     */
    List<Order> getPendingApprovalList();
}
