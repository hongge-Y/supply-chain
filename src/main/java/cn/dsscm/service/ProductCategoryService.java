package cn.dsscm.service;

import cn.dsscm.pojo.ProductCategory;
import java.util.List;

/**
 * 商品分类业务逻辑接口
 */
public interface ProductCategoryService {

    /**
     * 按父级ID获取子分类列表
     */
    List<ProductCategory> getCategoriesByParentId(Integer parentId);

    /**
     * 获取全部分类
     */
    List<ProductCategory> getAllCategories();

    /**
     * 根据ID获取分类
     */
    ProductCategory getCategoryById(Integer id);

    /**
     * 添加分类
     */
    boolean addCategory(ProductCategory category);

    /**
     * 修改分类
     */
    boolean modifyCategory(ProductCategory category);

    /**
     * 删除分类
     */
    boolean deleteCategory(Integer id);
}