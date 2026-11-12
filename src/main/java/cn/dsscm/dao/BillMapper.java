package cn.dsscm.dao;

import cn.dsscm.pojo.Bill;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 采购订单表数据访问接口
 */
public interface BillMapper {

    /**
     * 分页查询采购订单（支持按商品名称、供应商ID、是否支付过滤）
     */
    List<Bill> selectByPage(@Param("productName") String productName,
                            @Param("providerId") Integer providerId,
                            @Param("isPayment") Integer isPayment,
                            @Param("createdBy") Long createdBy,
                            @Param("start") int start,
                            @Param("size") int size);

    int selectCount(@Param("productName") String productName,
                    @Param("providerId") Integer providerId,
                    @Param("isPayment") Integer isPayment,
                    @Param("createdBy") Long createdBy);

    Bill selectById(@Param("id") Long id);

    int insertBill(Bill bill);

    int updateBill(Bill bill);

    int deleteBill(@Param("id") Long id);

    /**
     * 更新审批状态
     */
    void updateApprovalStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询所有待审批记录
     */
    List<Bill> selectPendingApprovals();
}
