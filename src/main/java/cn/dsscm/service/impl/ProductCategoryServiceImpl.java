package cn.dsscm.service.impl;

import cn.dsscm.dao.ProductCategoryMapper;
import cn.dsscm.pojo.ProductCategory;
import cn.dsscm.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类业务逻辑实现类
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper categoryMapper;

    @Override
    public List<ProductCategory> getCategoriesByParentId(Integer parentId) {
        return categoryMapper.selectByParentId(parentId);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryMapper.selectAll();
    }

    @Override
    public ProductCategory getCategoryById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public boolean addCategory(ProductCategory category) {
        return categoryMapper.insertCategory(category) > 0;
    }

    @Override
    public boolean modifyCategory(ProductCategory category) {
        return categoryMapper.updateCategory(category) > 0;
    }

    @Override
    public boolean deleteCategory(Integer id) {
        return categoryMapper.deleteCategory(id) > 0;
    }
}