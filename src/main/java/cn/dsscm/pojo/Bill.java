package cn.dsscm.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购订单实体类
 */
public class Bill {
    private Long id;                // 主键ID
    private String billCode;        // 账单编码
    private Long productId;         // 商品编号
    private String productName;     // 商品名称
    private String productDesc;     // 商品描述
    private String productUnit;     // 商品单位
    private BigDecimal productCount;// 商品数量
    private BigDecimal totalPrice;  // 商品总额
    private Integer isPayment;      // 是否支付（1:未支付 2:已支付）
    private Long createdBy;         // 创建者ID
    private Date creationDate;      // 创建时间
    private Long modifyBy;          // 更新者ID
    private Date modifyDate;        // 更新时间
    private Integer providerId;     // 供应商ID
    private Integer approvalStatus; // 审批状态：0=草稿 1=待审批 2=审批通过 3=审批驳回

    public Bill() {}

    public Bill(Long id, String billCode, Long productId, String productName,
                String productDesc, String productUnit, BigDecimal productCount,
                BigDecimal totalPrice, Integer isPayment, Long createdBy,
                Date creationDate, Long modifyBy, Date modifyDate, Integer providerId) {
        this.id = id;
        this.billCode = billCode;
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productUnit = productUnit;
        this.productCount = productCount;
        this.totalPrice = totalPrice;
        this.isPayment = isPayment;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.providerId = providerId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBillCode() { return billCode; }
    public void setBillCode(String billCode) { this.billCode = billCode; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductDesc() { return productDesc; }
    public void setProductDesc(String productDesc) { this.productDesc = productDesc; }
    public String getProductUnit() { return productUnit; }
    public void setProductUnit(String productUnit) { this.productUnit = productUnit; }
    public BigDecimal getProductCount() { return productCount; }
    public void setProductCount(BigDecimal productCount) { this.productCount = productCount; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public Integer getIsPayment() { return isPayment; }
    public void setIsPayment(Integer isPayment) { this.isPayment = isPayment; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Long getModifyBy() { return modifyBy; }
    public void setModifyBy(Long modifyBy) { this.modifyBy = modifyBy; }
    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }
    public Integer getProviderId() { return providerId; }
    public void setProviderId(Integer providerId) { this.providerId = providerId; }

    public Integer getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(Integer approvalStatus) { this.approvalStatus = approvalStatus; }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", billCode='" + billCode + '\'' +
                ", productName='" + productName + '\'' +
                ", totalPrice=" + totalPrice +
                ", isPayment=" + isPayment +
                '}';
    }
}