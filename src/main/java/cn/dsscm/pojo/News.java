package cn.dsscm.pojo;

import java.util.Date;

/**
 * 新闻实体类
 */
public class News {
    private Long id;            // 主键
    private String title;       // 标题
    private String content;     // 内容
    private Long createdBy;     // 创建者ID
    private Date creationDate;  // 创建时间
    private Long modifyBy;      // 更新者ID
    private Date modifyDate;    // 更新时间

    private Integer approvalStatus; // 审批状态：0=草稿 1=待审批 2=已通过 3=已驳回

    public News() {}

    public News(Long id, String title, String content, Long createdBy,
                Date creationDate, Long modifyBy, Date modifyDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
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
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}