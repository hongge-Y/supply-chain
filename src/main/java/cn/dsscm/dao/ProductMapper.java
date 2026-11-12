package cn.dsscm.dao;

import cn.dsscm.pojo.Product;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品表数据访问接口
 */
public interface ProductMapper {

    /**
     * 分页查询商品列表（按名称、分类等条件）
     */
    List<Product> selectByPage(@Param("name") String name,
                               @Param("categoryLevel1Id") Integer categoryLevel1Id,
                               @Param("categoryLevel2Id") Integer categoryLevel2Id,
                               @Param("createdBy") Long createdBy,
                               @Param("start") int start,
                               @Param("size") int size);

    int selectCount(@Param("name") String name,
                    @Param("categoryLevel1Id") Integer categoryLevel1Id,
                    @Param("categoryLevel2Id") Integer categoryLevel2Id,
                    @Param("createdBy") Long createdBy);

    int insertProduct(Product product);

    int updateProduct(Product product);

    int deleteProduct(@Param("id") Integer id); // 建议逻辑删除，但此处按物理删除实现

    Product selectById(@Param("id") Integer id);

    /**
     * 更新审批状态（Product的id为Integer类型）
     */
    void updateApprovalStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 查询所有待审批记录
     */
    List<Product> selectPendingApprovals();
}
