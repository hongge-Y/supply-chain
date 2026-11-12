package cn.dsscm.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售订单实体类
 */
public class Order {
    private Long id;                // 主键
    private String userName;        // 顾客姓名
    private String customerPhone;   // 顾客联系电话
    private String userAddress;     // 用户地址
    private Integer proCount;       // 商品数量
    private BigDecimal cost;        // 总消费
    private String serialNumber;    // 订单号
    private Integer status;         // 订单状态（1:待审核 2:审核通过 3:配货 4:卖家已发货 5:已收货）
    private Integer payType;        // 付款方式（1:在线支付 2:货到付款）
    private Long createdBy;         // 创建者ID
    private Date creationDate;      // 创建时间
    private Long modifyBy;          // 更新者ID
    private Date modifyDate;        // 更新时间
    private Integer approvalStatus; // 审批状态：0=草稿 1=待审批 2=审批通过 3=审批驳回

    public Order() {}

    public Order(Long id, String userName, String customerPhone, String userAddress,
                 Integer proCount, BigDecimal cost, String serialNumber,
                 Integer status, Integer payType, Long createdBy,
                 Date creationDate, Long modifyBy, Date modifyDate) {
        this.id = id;
        this.userName = userName;
        this.customerPhone = customerPhone;
        this.userAddress = userAddress;
        this.proCount = proCount;
        this.cost = cost;
        this.serialNumber = serialNumber;
        this.status = status;
        this.payType = payType;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getUserAddress() { return userAddress; }
    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }
    public Integer getProCount() { return proCount; }
    public void setProCount(Integer proCount) { this.proCount = proCount; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getPayType() { return payType; }
    public void setPayType(Integer payType) { this.payType = payType; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Long getModifyBy() { return modifyBy; }
    public void setModifyBy(Long modifyBy) { this.modifyBy = modifyBy; }
    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }

    public Integer getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(Integer approvalStatus) { this.approvalStatus = approvalStatus; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", cost=" + cost +
                ", status=" + status +
                '}';
    }
}