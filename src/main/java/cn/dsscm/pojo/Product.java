package cn.dsscm.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
public class Product {
    private Integer id;                 // 主键 — TODO: 与其他实体不一致（其他为 Long），如需统一需改 DB + 全部 Mapper
    private String name;                // 商品名称
    private String description;         // 描述
    private BigDecimal price;           // 价格（保留两位小数）
    private String placement;           // 摆放位置
    private BigDecimal stock;           // 库存数量（保留两位小数）
    private Integer categoryLevel1Id;   // 一级分类ID
    private Integer categoryLevel2Id;   // 二级分类ID
    private Integer categoryLevel3Id;   // 三级分类ID
    private String fileName;            // 图片文件名称
    private Integer isDelete;           // 是否删除（1:删除 0:未删除）
    private Long createdBy;             // 创建者ID
    private Date creationDate;          // 创建时间
    private Long modifyBy;              // 更新者ID
    private Date modifyDate;            // 更新时间
    private Integer approvalStatus;     // 审批状态：0=草稿 1=待审批 2=审批通过 3=审批驳回

    public Product() {}

    public Product(Integer id, String name, String description, BigDecimal price,
                   String placement, BigDecimal stock, Integer categoryLevel1Id,
                   Integer categoryLevel2Id, Integer categoryLevel3Id, String fileName,
                   Integer isDelete, Long createdBy, Date creationDate, Long modifyBy,
                   Date modifyDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.placement = placement;
        this.stock = stock;
        this.categoryLevel1Id = categoryLevel1Id;
        this.categoryLevel2Id = categoryLevel2Id;
        this.categoryLevel3Id = categoryLevel3Id;
        this.fileName = fileName;
        this.isDelete = isDelete;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getPlacement() { return placement; }
    public void setPlacement(String placement) { this.placement = placement; }
    public BigDecimal getStock() { return stock; }
    public void setStock(BigDecimal stock) { this.stock = stock; }
    public Integer getCategoryLevel1Id() { return categoryLevel1Id; }
    public void setCategoryLevel1Id(Integer categoryLevel1Id) { this.categoryLevel1Id = categoryLevel1Id; }
    public Integer getCategoryLevel2Id() { return categoryLevel2Id; }
    public void setCategoryLevel2Id(Integer categoryLevel2Id) { this.categoryLevel2Id = categoryLevel2Id; }
    public Integer getCategoryLevel3Id() { return categoryLevel3Id; }
    public void setCategoryLevel3Id(Integer categoryLevel3Id) { this.categoryLevel3Id = categoryLevel3Id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public Integer getIsDelete() { return isDelete; }
    public void setIsDelete(Integer isDelete) { this.isDelete = isDelete; }
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
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}