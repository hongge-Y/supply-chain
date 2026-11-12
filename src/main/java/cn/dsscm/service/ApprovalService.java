package cn.dsscm.service;

import cn.dsscm.pojo.Approval;
import java.util.List;

/**
 * 审批业务逻辑接口
 * 处理用户对业务数据的增/删/改操作的审批请求。
 */
public interface ApprovalService {
    /**
     * 提交审批申请
     *
     * @param approval 审批申请对象
     */
    void apply(Approval approval);

    /**
     * 获取待审批列表
     *
     * @return 待审批申请列表
     */
    List<Approval> getPendingList();

    /**
     * 获取我的申请记录
     *
     * @param applicantId 申请人ID
     * @return 申请记录列表
     */
    List<Approval> getMyApplications(Long applicantId);

    /**
     * 根据ID获取审批申请
     *
     * @param id 审批ID
     * @return 审批申请对象
     */
    Approval getById(Long id);

    /**
     * 审批通过
     *
     * @param id          审批ID
     * @param approverId  审批人ID
     * @param comment     审批意见
     */
    void approve(Long id, Long approverId, String comment);

    /**
     * 审批拒绝
     *
     * @param id          审批ID
     * @param approverId  审批人ID
     * @param comment     审批意见
     */
    void reject(Long id, Long approverId, String comment);

    /**
     * 执行审批通过后的实际操作
     *
     * @param approval 审批通过后的申请对象
     */
    void executeApproved(Approval approval);
}