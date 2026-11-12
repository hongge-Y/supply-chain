package cn.dsscm.service.impl;

import cn.dsscm.dao.OrderMapper;
import cn.dsscm.dao.OrderDetailMapper;
import cn.dsscm.pojo.Order;
import cn.dsscm.pojo.OrderDetail;
import cn.dsscm.service.OrderService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 销售订单业务逻辑实现类
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<Order> getOrderList(String userName, Integer status, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return orderMapper.selectByPage(userName, status, null, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getOrderCount(String userName, Integer status) {
        return orderMapper.selectCount(userName, status, null);
    }

    @Override
    public boolean addOrder(Order order, List<OrderDetail> details) {
        order.setCreationDate(new Date());
        // 1. 插入订单主表，获取生成的订单ID
        int row = orderMapper.insertOrder(order);
        if (row > 0 && details != null && details.size() > 0) {
            // 2. 为明细设置订单ID并批量插入
            for (OrderDetail detail : details) {
                detail.setOrderId(order.getId().intValue());
            }
            orderDetailMapper.insertBatch(details);
        }
        return row > 0;
    }

    @Override
    public boolean modifyOrder(Order order) {
        order.setModifyDate(new Date());
        return orderMapper.updateOrder(order) > 0;
    }

    @Override
    public boolean deleteOrder(Long id) {
        // 先删除明细，再删除订单（保证数据一致性）
        orderDetailMapper.deleteByOrderId(id.intValue());
        return orderMapper.deleteOrder(id) > 0;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        return orderDetailMapper.selectByOrderId(orderId);
    }

    @Override
    public boolean submitForApproval(Long id) {
        orderMapper.updateApprovalStatus(id, 1);
        return true;
    }

    @Override
    public boolean approve(Long id) {
        orderMapper.updateApprovalStatus(id, 2);
        return true;
    }

    @Override
    public boolean reject(Long id) {
        orderMapper.updateApprovalStatus(id, 0); // 驳回 → 退回草稿
        return true;
    }

    @Override
    public int getPendingApprovalCount() {
        return orderMapper.selectPendingApprovals().size();
    }

    @Override
    public List<Order> getMyOrderList(Long userId, String userName, Integer status, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return orderMapper.selectByPage(userName, status, userId, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getMyOrderCount(Long userId, String userName, Integer status) {
        return orderMapper.selectCount(userName, status, userId);
    }

    @Override
    public List<Order> getPendingApprovalList() {
        return orderMapper.selectPendingApprovals();
    }
}
