package cn.dsscm.service.impl;

import cn.dsscm.dao.ProviderMapper;
import cn.dsscm.pojo.Provider;
import cn.dsscm.service.ProviderService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 供应商业务逻辑实现类
 */
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProviderList(String proCode, String proName, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return providerMapper.selectByPage(proCode, proName, null, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getProviderCount(String proCode, String proName) {
        return providerMapper.selectCount(proCode, proName, null);
    }

    @Override
    public boolean addProvider(Provider provider) {
        provider.setCreationDate(new Date());
        if (provider.getApprovalStatus() == null) {
            provider.setApprovalStatus(0); // 仅未设置时默认草稿
        }
        return providerMapper.insertProvider(provider) > 0;
    }

    @Override
    public boolean modifyProvider(Provider provider) {
        provider.setModifyDate(new Date());
        return providerMapper.updateProvider(provider) > 0;
    }

    @Override
    public boolean deleteProvider(Long id) {
        return providerMapper.deleteProvider(id) > 0;
    }

    @Override
    public Provider getProviderById(Long id) {
        return providerMapper.selectById(id);
    }

    @Override
    public boolean submitForApproval(Long id) {
        providerMapper.updateApprovalStatus(id, 1);
        return true;
    }

    @Override
    public boolean approve(Long id) {
        providerMapper.updateApprovalStatus(id, 2);
        return true;
    }

    @Override
    public boolean reject(Long id) {
        providerMapper.updateApprovalStatus(id, 0); // 驳回 → 退回草稿
        return true;
    }

    @Override
    public int getPendingApprovalCount() {
        return providerMapper.selectPendingApprovals().size();
    }

    @Override
    public List<Provider> getMyProviderList(Long userId, String proCode, String proName, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return providerMapper.selectByPage(proCode, proName, userId, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getMyProviderCount(Long userId, String proCode, String proName) {
        return providerMapper.selectCount(proCode, proName, userId);
    }

    @Override
    public List<Provider> getPendingApprovalList() {
        return providerMapper.selectPendingApprovals();
    }
}
