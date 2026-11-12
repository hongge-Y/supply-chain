package cn.dsscm.dao;

import cn.dsscm.pojo.Order;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 销售订单表数据访问接口
 */
public interface OrderMapper {

    /**
     * 分页查询销售订单（可按顾客姓名、订单状态过滤）
     */
    List<Order> selectByPage(@Param("userName") String userName,
                             @Param("status") Integer status,
                             @Param("createdBy") Long createdBy,
                             @Param("start") int start,
                             @Param("size") int size);

    int selectCount(@Param("userName") String userName,
                    @Param("status") Integer status,
                    @Param("createdBy") Long createdBy);

    Order selectById(@Param("id") Long id);

    int insertOrder(Order order);

    int updateOrder(Order order);

    int deleteOrder(@Param("id") Long id);

    /**
     * 更新审批状态
     */
    void updateApprovalStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询所有待审批记录
     */
    List<Order> selectPendingApprovals();
}
