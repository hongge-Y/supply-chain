package cn.dsscm.pojo;

import java.util.Date;

/**
 * 用户实体类
 */
public class User {
    private Long id;                // 主键ID
    private String userCode;        // 用户编码
    private String userName;        // 用户真实姓名
    private String userPassword;    // 用户密码
    private Integer gender;         // 性别（1:女、2:男）
    private Date birthday;          // 出生日期
    private String email;           // 邮箱
    private String phone;           // 手机号
    private String address;         // 地址
    private String userDesc;        // 简介
    private Integer userRole;       // 用户角色ID（取自角色表 tb_role.id）
    private String imgPath;         // 用户照片路径
    private Integer status;         // 用户状态：0=待审核（=草稿） 1=正常 2=已禁用 3=已提交审批（=待审批）
    private String registerReason;  // 注册申请理由
    private Long createdBy;         // 创建者ID
    private Date creationDate;      // 创建时间
    private Long modifyBy;          // 最后修改者ID
    private Date modifyDate;        // 最后修改时间

    public User() {}

    public User(Long id, String userCode, String userName, String userPassword,
                Integer gender, Date birthday, String email, String phone,
                String address, String userDesc, Integer userRole, String imgPath,
                Integer status, String registerReason,
                Long createdBy, Date creationDate, Long modifyBy, Date modifyDate) {
        this.id = id;
        this.userCode = userCode;
        this.userName = userName;
        this.userPassword = userPassword;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userDesc = userDesc;
        this.userRole = userRole;
        this.imgPath = imgPath;
        this.status = status;
        this.registerReason = registerReason;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
    }

    // Getter & Setter 方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserCode() { return userCode; }
    public void setUserCode(String userCode) { this.userCode = userCode; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public Integer getGender() { return gender; }
    public void setGender(Integer gender) { this.gender = gender; }
    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getUserDesc() { return userDesc; }
    public void setUserDesc(String userDesc) { this.userDesc = userDesc; }
    public Integer getUserRole() { return userRole; }
    public void setUserRole(Integer userRole) { this.userRole = userRole; }
    public String getImgPath() { return imgPath; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Long getModifyBy() { return modifyBy; }
    public void setModifyBy(Long modifyBy) { this.modifyBy = modifyBy; }
    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getRegisterReason() { return registerReason; }
    public void setRegisterReason(String registerReason) { this.registerReason = registerReason; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", userRole=" + userRole +
                ", status=" + status +
                '}';
    }
}