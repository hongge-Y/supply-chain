package cn.dsscm.dao;

import cn.dsscm.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品分类表数据访问接口
 */
public interface ProductCategoryMapper {

    /**
     * 按父级ID查询子分类列表
     * @param parentId 父级ID（0表示一级分类）
     */
    List<ProductCategory> selectByParentId(@Param("parentId") Integer parentId);

    /**
     * 查询所有分类
     */
    List<ProductCategory> selectAll();

    /**
     * 按ID查询
     */
    ProductCategory selectById(@Param("id") Integer id);

    /**
     * 新增商品分类
     *
     * @param category 分类对象
     * @return 受影响行数
     */
    int insertCategory(ProductCategory category);

    /**
     * 更新商品分类
     *
     * @param category 分类对象
     * @return 受影响行数
     */
    int updateCategory(ProductCategory category);

    /**
     * 删除商品分类
     *
     * @param id 分类ID
     * @return 受影响行数
     */
    int deleteCategory(@Param("id") Integer id);
}