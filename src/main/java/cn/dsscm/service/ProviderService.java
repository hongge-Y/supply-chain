package cn.dsscm.service;

import cn.dsscm.pojo.Provider;
import java.util.List;

/**
 * 供应商业务逻辑接口
 */
public interface ProviderService {

    /**
     * 分页查询供应商列表（只返回审批通过的数据）
     */
    List<Provider> getProviderList(String proCode, String proName, int pageIndex, int pageSize);

    /**
     * 统计符合条件的供应商数量
     */
    int getProviderCount(String proCode, String proName);

    /**
     * 添加供应商
     */
    boolean addProvider(Provider provider);

    /**
     * 修改供应商
     */
    boolean modifyProvider(Provider provider);

    /**
     * 删除供应商
     */
    boolean deleteProvider(Long id);

    /**
     * 根据ID查询供应商
     */
    Provider getProviderById(Long id);

    /**
     * 提交审批：草稿→待审批
     */
    boolean submitForApproval(Long id);

    /**
     * 审批通过：待审批→通过
     */
    boolean approve(Long id);

    /**
     * 审批驳回：待审批→驳回
     */
    boolean reject(Long id);

    /**
     * 待审批数量
     */
    int getPendingApprovalCount();

    /**
     * 分页查询当前用户能看到的供应商列表（自己的草稿/驳回 + 所有审批通过的）
     */
    List<Provider> getMyProviderList(Long userId, String proCode, String proName, int pageIndex, int pageSize);

    /**
     * 统计当前用户能看到的供应商数量
     */
    int getMyProviderCount(Long userId, String proCode, String proName);

    /**
     * 查询所有待审批的供应商列表
     */
    List<Provider> getPendingApprovalList();
}
