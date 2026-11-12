package cn.dsscm.dao;

import cn.dsscm.pojo.OrderDetail;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 销售订单明细表数据访问接口
 */
public interface OrderDetailMapper {

    /**
     * 根据订单ID查询明细列表
     */
    List<OrderDetail> selectByOrderId(@Param("orderId") Integer orderId);

    /**
     * 批量插入订单明细
     */
    int insertBatch(@Param("details") List<OrderDetail> details);

    /**
     * 删除某个订单的所有明细
     */
    int deleteByOrderId(@Param("orderId") Integer orderId);
}