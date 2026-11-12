package cn.dsscm.service.impl;

import cn.dsscm.dao.BillMapper;
import cn.dsscm.pojo.Bill;
import cn.dsscm.service.BillService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 采购订单业务逻辑实现类
 */
@Service
@Transactional
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Bill> getBillList(String productName, Integer providerId,
                                  Integer isPayment, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return billMapper.selectByPage(productName, providerId, isPayment, null, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getBillCount(String productName, Integer providerId, Integer isPayment) {
        return billMapper.selectCount(productName, providerId, isPayment, null);
    }

    @Override
    public boolean addBill(Bill bill) {
        bill.setCreationDate(new Date());
        if (bill.getApprovalStatus() == null) {
            bill.setApprovalStatus(0); // 仅未设置时默认草稿
        }
        return billMapper.insertBill(bill) > 0;
    }

    @Override
    public boolean modifyBill(Bill bill) {
        bill.setModifyDate(new Date());
        return billMapper.updateBill(bill) > 0;
    }

    @Override
    public boolean deleteBill(Long id) {
        return billMapper.deleteBill(id) > 0;
    }

    @Override
    public Bill getBillById(Long id) {
        return billMapper.selectById(id);
    }

    @Override
    public boolean submitForApproval(Long id) {
        billMapper.updateApprovalStatus(id, 1);
        return true;
    }

    @Override
    public boolean approve(Long id) {
        billMapper.updateApprovalStatus(id, 2);
        return true;
    }

    @Override
    public boolean reject(Long id) {
        billMapper.updateApprovalStatus(id, 0); // 驳回 → 退回草稿
        return true;
    }

    @Override
    public int getPendingApprovalCount() {
        return billMapper.selectPendingApprovals().size();
    }

    @Override
    public List<Bill> getMyBillList(Long userId, String productName, Integer providerId,
                                    Integer isPayment, int pageIndex, int pageSize) {
        PageUtil pageUtil = new PageUtil(pageIndex, pageSize, 0);
        return billMapper.selectByPage(productName, providerId, isPayment, userId, pageUtil.getStart(), pageSize);
    }

    @Override
    public int getMyBillCount(Long userId, String productName, Integer providerId, Integer isPayment) {
        return billMapper.selectCount(productName, providerId, isPayment, userId);
    }

    @Override
    public List<Bill> getPendingApprovalList() {
        return billMapper.selectPendingApprovals();
    }
}
