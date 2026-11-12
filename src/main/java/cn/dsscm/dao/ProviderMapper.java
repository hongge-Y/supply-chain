package cn.dsscm.dao;

import cn.dsscm.pojo.Provider;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 供应商表数据访问接口
 */
public interface ProviderMapper {

    /**
     * 条件分页查询供应商
     * @param proCode 供应商编码（模糊）
     * @param proName 供应商名称（模糊）
     * @param start   起始行
     * @param size    页大小
     */
    List<Provider> selectByPage(@Param("proCode") String proCode,
                                @Param("proName") String proName,
                                @Param("createdBy") Long createdBy,
                                @Param("start") int start,
                                @Param("size") int size);

    int selectCount(@Param("proCode") String proCode,
                    @Param("proName") String proName,
                    @Param("createdBy") Long createdBy);

    int insertProvider(Provider provider);

    int updateProvider(Provider provider);

    int deleteProvider(@Param("id") Long id);

    Provider selectById(@Param("id") Long id);

    /**
     * 更新审批状态
     */
    void updateApprovalStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查询所有待审批记录
     */
    List<Provider> selectPendingApprovals();
}
