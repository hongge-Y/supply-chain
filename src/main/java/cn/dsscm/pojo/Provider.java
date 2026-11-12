package cn.dsscm.pojo;

import java.util.Date;

/**
 * 供应商实体类
 */
public class Provider {
    private Long id;                    // 主键ID
    private String proCode;             // 供应商编码
    private String proName;             // 供应商名称
    private String proDesc;             // 供应商详细描述
    private String proContact;          // 联系人
    private String proPhone;            // 联系电话
    private String proAddress;          // 地址
    private String proFax;              // 传真
    private Long createdBy;             // 创建者ID
    private Date creationDate;          // 创建时间
    private Date modifyDate;            // 更新时间
    private Long modifyBy;              // 更新者ID
    private String companyLicPicPath;   // 营业执照存储路径
    private String orgCodePicPath;      // 组织机构代码证存储路径
    private Integer approvalStatus;     // 审批状态：0=草稿 1=待审批 2=审批通过 3=审批驳回

    public Provider() {}

    public Provider(Long id, String proCode, String proName, String proDesc,
                    String proContact, String proPhone, String proAddress,
                    String proFax, Long createdBy, Date creationDate,
                    Date modifyDate, Long modifyBy, String companyLicPicPath,
                    String orgCodePicPath) {
        this.id = id;
        this.proCode = proCode;
        this.proName = proName;
        this.proDesc = proDesc;
        this.proContact = proContact;
        this.proPhone = proPhone;
        this.proAddress = proAddress;
        this.proFax = proFax;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyDate = modifyDate;
        this.modifyBy = modifyBy;
        this.companyLicPicPath = companyLicPicPath;
        this.orgCodePicPath = orgCodePicPath;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProCode() { return proCode; }
    public void setProCode(String proCode) { this.proCode = proCode; }
    public String getProName() { return proName; }
    public void setProName(String proName) { this.proName = proName; }
    public String getProDesc() { return proDesc; }
    public void setProDesc(String proDesc) { this.proDesc = proDesc; }
    public String getProContact() { return proContact; }
    public void setProContact(String proContact) { this.proContact = proContact; }
    public String getProPhone() { return proPhone; }
    public void setProPhone(String proPhone) { this.proPhone = proPhone; }
    public String getProAddress() { return proAddress; }
    public void setProAddress(String proAddress) { this.proAddress = proAddress; }
    public String getProFax() { return proFax; }
    public void setProFax(String proFax) { this.proFax = proFax; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }
    public Long getModifyBy() { return modifyBy; }
    public void setModifyBy(Long modifyBy) { this.modifyBy = modifyBy; }
    public String getCompanyLicPicPath() { return companyLicPicPath; }
    public void setCompanyLicPicPath(String companyLicPicPath) { this.companyLicPicPath = companyLicPicPath; }
    public String getOrgCodePicPath() { return orgCodePicPath; }
    public void setOrgCodePicPath(String orgCodePicPath) { this.orgCodePicPath = orgCodePicPath; }

    public Integer getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(Integer approvalStatus) { this.approvalStatus = approvalStatus; }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proContact='" + proContact + '\'' +
                '}';
    }
}