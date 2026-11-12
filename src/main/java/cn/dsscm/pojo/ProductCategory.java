package cn.dsscm.pojo;

/**
 * 商品分类实体类
 */
public class ProductCategory {
    private Integer id;         // 主键
    private String name;        // 分类名称
    private Integer parentId;   // 父级目录ID（0表示一级分类）
    private Integer type;       // 级别（1:一级 2:二级 3:三级）
    private String iconClass;   // 图标样式类名

    public ProductCategory() {}

    public ProductCategory(Integer id, String name, Integer parentId, Integer type, String iconClass) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
        this.iconClass = iconClass;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public String getIconClass() { return iconClass; }
    public void setIconClass(String iconClass) { this.iconClass = iconClass; }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", type=" + type +
                '}';
    }
}