package cn.dsscm.service.impl;

import cn.dsscm.dao.ProductMapper;
import cn.dsscm.pojo.Product;
import cn.dsscm.service.ProductService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 商品业务逻辑实现类
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductList(String name, Integer categoryLevel1Id,
                                        Integer categoryLevel2Id, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return productMapper.selectByPage(name, categoryLevel1Id, categoryLevel2Id, null, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getProductCount(String name, Integer categoryLevel1Id, Integer categoryLevel2Id) {
        return productMapper.selectCount(name, categoryLevel1Id, categoryLevel2Id, null);
    }

    @Override
    public boolean addProduct(Product product) {
        product.setCreationDate(new Date());
        if (product.getApprovalStatus() == null) {
            product.setApprovalStatus(0); // 仅未设置时默认草稿
        }
        // 价格和库存不能为负数
        if (product.getPrice() != null && product.getPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("商品价格不能为负数");
        }
        if (product.getStock() != null && product.getStock().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("商品库存不能为负数");
        }
        return productMapper.insertProduct(product) > 0;
    }

    @Override
    public boolean modifyProduct(Product product) {
        product.setModifyDate(new Date());
        // 价格和库存不能为负数
        if (product.getPrice() != null && product.getPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("商品价格不能为负数");
        }
        if (product.getStock() != null && product.getStock().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("商品库存不能为负数");
        }
        return productMapper.updateProduct(product) > 0;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return productMapper.deleteProduct(id) > 0;
    }

    @Override
    public Product getProductById(Integer id) {
        return productMapper.selectById(id);
    }

    @Override
    public boolean submitForApproval(Integer id) {
        productMapper.updateApprovalStatus(id, 1);
        return true;
    }

    @Override
    public boolean approve(Integer id) {
        productMapper.updateApprovalStatus(id, 2);
        return true;
    }

    @Override
    public boolean reject(Integer id) {
        productMapper.updateApprovalStatus(id, 0); // 驳回 → 退回草稿
        return true;
    }

    @Override
    public int getPendingApprovalCount() {
        return productMapper.selectPendingApprovals().size();
    }

    @Override
    public List<Product> getMyProductList(Long userId, String name, Integer categoryLevel1Id,
                                          Integer categoryLevel2Id, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return productMapper.selectByPage(name, categoryLevel1Id, categoryLevel2Id, userId, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getMyProductCount(Long userId, String name, Integer categoryLevel1Id, Integer categoryLevel2Id) {
        return productMapper.selectCount(name, categoryLevel1Id, categoryLevel2Id, userId);
    }

    @Override
    public List<Product> getPendingApprovalList() {
        return productMapper.selectPendingApprovals();
    }
}
