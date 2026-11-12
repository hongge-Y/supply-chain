package cn.dsscm.pojo;

import java.util.Date;

/**
 * 角色实体类
 */
public class Role {
    private Long id;                // 主键ID
    private String roleCode;        // 角色编码
    private String roleName;        // 角色名称
    private Long createdBy;         // 创建者ID
    private Date creationDate;      // 创建时间
    private Long modifyBy;          // 修改者ID
    private Date modifyDate;        // 修改时间

    public Role() {}

    public Role(Long id, String roleCode, String roleName, Long createdBy,
                Date creationDate, Long modifyBy, Date modifyDate) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoleCode() { return roleCode; }
    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Long getModifyBy() { return modifyBy; }
    public void setModifyBy(Long modifyBy) { this.modifyBy = modifyBy; }
    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}