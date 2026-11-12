package cn.dsscm.pojo;

import java.util.Date;

/**
 * 审批申请实体类
 * 用于记录用户对业务数据的增/删/改操作的审批请求，
 * 支持工作流：申请 → 审批通过/拒绝 → 执行实际操作。
 */
public class Approval {

    private Long id;
    private String module;          // 模块：user/provider/product/bill/order/news
    private String action;          // 操作：add/update/delete
    private Long recordId;          // 操作记录ID（新增时为 null）
    private Long applicantId;       // 申请人ID
    private String applyReason;     // 申请原因
    private Integer status;         // 状态：0待审批，1已通过，2已拒绝
    private Long approverId;        // 审批人ID
    private String approvalComment; // 审批意见
    private Date applyDate;         // 申请时间
    private Date approvalDate;      // 审批时间
    private String detailJson;      // 暂存新增/修改的数据（JSON）

    // 非数据库字段
    private String applicantName;   // 申请人名称

    public Approval() {}

    public Approval(Long id, String module, String action, Long recordId,
                    Long applicantId, String applyReason, Integer status,
                    Long approverId, String approvalComment, Date applyDate,
                    Date approvalDate, String detailJson) {
        this.id = id;
        this.module = module;
        this.action = action;
        this.recordId = recordId;
        this.applicantId = applicantId;
        this.applyReason = applyReason;
        this.status = status;
        this.approverId = approverId;
        this.approvalComment = approvalComment;
        this.applyDate = applyDate;
        this.approvalDate = approvalDate;
        this.detailJson = detailJson;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }

    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }

    public String getApplyReason() { return applyReason; }
    public void setApplyReason(String applyReason) { this.applyReason = applyReason; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Long getApproverId() { return approverId; }
    public void setApproverId(Long approverId) { this.approverId = approverId; }

    public String getApprovalComment() { return approvalComment; }
    public void setApprovalComment(String approvalComment) { this.approvalComment = approvalComment; }

    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }

    public Date getApprovalDate() { return approvalDate; }
    public void setApprovalDate(Date approvalDate) { this.approvalDate = approvalDate; }

    public String getDetailJson() { return detailJson; }
    public void setDetailJson(String detailJson) { this.detailJson = detailJson; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    /**
     * 获取模块的中文名称
     *
     * @return 中文模块名
     */
    public String getModuleName() {
        if (module == null) return "";
        switch (module) {
            case "user": return "用户管理";
            case "provider": return "供应商管理";
            case "product": return "商品管理";
            case "bill": return "采购订单";
            case "order": return "销售订单";
            case "news": return "新闻公告";
            case "category": return "商品分类";
            default: return module;
        }
    }

    /**
     * 获取操作的中文名称
     *
     * @return 中文操作名（新增/修改/删除）
     */
    public String getActionName() {
        if (action == null) return "";
        switch (action) {
            case "add": return "新增";
            case "update": return "修改";
            case "delete": return "删除";
            default: return action;
        }
    }
}