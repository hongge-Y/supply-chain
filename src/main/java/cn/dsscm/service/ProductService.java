package cn.dsscm.service;

import cn.dsscm.pojo.Product;
import java.util.List;

/**
 * 商品业务逻辑接口
 */
public interface ProductService {

    /**
     * 分页查询商品列表（只返回审批通过的数据）
     */
    List<Product> getProductList(String name, Integer categoryLevel1Id,
                                 Integer categoryLevel2Id, int pageIndex, int pageSize);

    /**
     * 统计商品数量
     */
    int getProductCount(String name, Integer categoryLevel1Id, Integer categoryLevel2Id);

    /**
     * 添加商品
     */
    boolean addProduct(Product product);

    /**
     * 修改商品
     */
    boolean modifyProduct(Product product);

    /**
     * 删除商品（物理删除）
     */
    boolean deleteProduct(Integer id);

    /**
     * 根据ID查询商品
     */
    Product getProductById(Integer id);

    /**
     * 提交审批：草稿→待审批
     */
    boolean submitForApproval(Integer id);

    /**
     * 审批通过：待审批→通过
     */
    boolean approve(Integer id);

    /**
     * 审批驳回：待审批→驳回
     */
    boolean reject(Integer id);

    /**
     * 待审批数量
     */
    int getPendingApprovalCount();

    /**
     * 分页查询当前用户能看到的商品列表（自己的草稿/驳回 + 所有审批通过的）
     */
    List<Product> getMyProductList(Long userId, String name, Integer categoryLevel1Id,
                                   Integer categoryLevel2Id, int pageIndex, int pageSize);

    /**
     * 统计当前用户能看到的商品数量
     */
    int getMyProductCount(Long userId, String name, Integer categoryLevel1Id, Integer categoryLevel2Id);

    /**
     * 查询所有待审批的商品列表
     */
    List<Product> getPendingApprovalList();
}
