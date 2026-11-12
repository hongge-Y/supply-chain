package cn.dsscm.service;

import cn.dsscm.pojo.Bill;
import java.util.List;

/**
 * 采购订单业务逻辑接口
 */
public interface BillService {

    /**
     * 分页查询采购订单（只返回审批通过的数据）
     */
    List<Bill> getBillList(String productName, Integer providerId,
                           Integer isPayment, int pageIndex, int pageSize);

    /**
     * 统计采购订单数量
     */
    int getBillCount(String productName, Integer providerId, Integer isPayment);

    /**
     * 添加采购订单
     */
    boolean addBill(Bill bill);

    /**
     * 修改采购订单
     */
    boolean modifyBill(Bill bill);

    /**
     * 删除采购订单
     */
    boolean deleteBill(Long id);

    /**
     * 根据ID查询采购订单
     */
    Bill getBillById(Long id);

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
     * 分页查询当前用户能看到的采购订单列表（自己的草稿/驳回 + 所有审批通过的）
     */
    List<Bill> getMyBillList(Long userId, String productName, Integer providerId,
                             Integer isPayment, int pageIndex, int pageSize);

    /**
     * 统计当前用户能看到的采购订单数量
     */
    int getMyBillCount(Long userId, String productName, Integer providerId, Integer isPayment);

    /**
     * 查询所有待审批的采购订单列表
     */
    List<Bill> getPendingApprovalList();
}
