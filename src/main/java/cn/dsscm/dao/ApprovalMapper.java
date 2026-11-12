package cn.dsscm.dao;

import cn.dsscm.pojo.Approval;
import java.util.List;

/**
 * 审批申请数据访问接口
 */
public interface ApprovalMapper {
    /**
     * 新增审批申请记录
     *
     * @param approval 审批申请对象
     * @return 受影响行数
     */
    int insert(Approval approval);

    /**
     * 查询所有待审批记录
     *
     * @return 待审批列表
     */
    List<Approval> selectPending();

    /**
     * 根据申请人ID查询其申请记录
     *
     * @param applicantId 申请人ID
     * @return 申请记录列表
     */
    List<Approval> selectByApplicant(Long applicantId);

    /**
     * 根据ID查询审批申请
     *
     * @param id 审批ID
     * @return 审批申请对象
     */
    Approval selectById(Long id);

    /**
     * 审批后更新状态、审批人、意见
     *
     * @param approval 含更新信息的审批对象
     * @return 受影响行数
     */
    int updateStatus(Approval approval);

    /**
     * 登记业务记录ID（新增审批用）
     *
     * @param approval 含业务记录ID的审批对象
     * @return 受影响行数
     */
    int updateRecordId(Approval approval);
}