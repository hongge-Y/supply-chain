package cn.dsscm.service.impl;

import cn.dsscm.dao.ApprovalMapper;
import cn.dsscm.pojo.*;
import cn.dsscm.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;

@Service
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(ApprovalServiceImpl.class.getName());

    static {
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @Autowired
    private ApprovalMapper approvalMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BillService billService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private NewsService newsService;

    @Autowired
    private ProductCategoryService categoryService;

    @Override
    public void apply(Approval approval) {
        approval.setStatus(0);
        approvalMapper.insert(approval);

        // 新增：提前创建业务记录（待审批），供列表页可见
        if ("add".equals(approval.getAction())) {
            Long newId = createPendingRecord(approval);
            if (newId != null) {
                approval.setRecordId(newId);
                approvalMapper.updateRecordId(approval);
            }
        } else if (approval.getRecordId() != null) {
            // 修改/删除：锁住业务记录状态为"待审批"
            setBusinessPending(approval.getModule(), approval.getRecordId());
        }
    }

    @Override
    public List<Approval> getPendingList() {
        return approvalMapper.selectPending();
    }

    @Override
    public List<Approval> getMyApplications(Long applicantId) {
        return approvalMapper.selectByApplicant(applicantId);
    }

    @Override
    public Approval getById(Long id) {
        return approvalMapper.selectById(id);
    }

    @Override
    public void approve(Long id, Long approverId, String comment) {
        Approval approval = approvalMapper.selectById(id);
        if (approval == null || approval.getStatus() != 0) return;
        approval.setStatus(1);
        approval.setApproverId(approverId);
        approval.setApprovalComment(comment);
        approvalMapper.updateStatus(approval);
        // 执行实际操作
        executeApproved(approval);
        // 同步业务记录状态为已通过（delete 操作无需再更新，记录已删除）
        if (!"delete".equals(approval.getAction()) && approval.getRecordId() != null) {
            updateBusinessStatus(approval.getModule(), approval.getRecordId(), 2);
        }
    }

    @Override
    public void reject(Long id, Long approverId, String comment) {
        Approval approval = approvalMapper.selectById(id);
        if (approval == null || approval.getStatus() != 0) return;
        approval.setStatus(2);
        approval.setApproverId(approverId);
        approval.setApprovalComment(comment);
        approvalMapper.updateStatus(approval);
        // 驳回：新增则删除记录，修改/删除则标记已驳回
        if (approval.getRecordId() != null) {
            if ("add".equals(approval.getAction())) {
                deletePendingRecord(approval.getModule(), approval.getRecordId());
            } else {
                updateBusinessStatus(approval.getModule(), approval.getRecordId(), 3);
            }
        }
    }

    @Override
    public void executeApproved(Approval approval) {
        String module = approval.getModule();
        String action = approval.getAction();
        Long recordId = approval.getRecordId();

        try {
            // 删除操作：直接使用 recordId
            if ("delete".equals(action)) {
                if (recordId == null) return;
                switch (module) {
                    case "user":
                        userService.updateUserStatus(recordId, 2); // 禁用用户而非物理删除
                        break;
                    case "provider":
                        providerService.deleteProvider(recordId);
                        break;
                    case "product":
                        productService.deleteProduct(recordId.intValue()); // Product id 为 Integer
                        break;
                    case "bill":
                        billService.deleteBill(recordId);
                        break;
                    case "order":
                        orderService.deleteOrder(recordId);
                        break;
                    case "news":
                        newsService.deleteNews(recordId);
                        break;
                    case "category":
                        categoryService.deleteCategory(recordId.intValue());
                        break;
                }
                return;
            }

            // 新增或修改：需要从 detailJson 中解析数据
            String detailJson = approval.getDetailJson();
            if (detailJson == null || detailJson.isEmpty()) return;

            Map<String, Object> map = OBJECT_MAPPER.readValue(detailJson, Map.class);
            switch (module) {
                case "user": {
                    User user = OBJECT_MAPPER.convertValue(map, User.class);
                    if ("add".equals(action)) {
                        user.setId(recordId);
                        user.setStatus(1);
                        userService.modifyUser(user);
                    } else if ("update".equals(action)) {
                        userService.modifyUser(user);
                    }
                    break;
                }
                case "provider": {
                    Provider provider = OBJECT_MAPPER.convertValue(map, Provider.class);
                    if ("add".equals(action)) {
                        provider.setId(recordId);
                        provider.setApprovalStatus(2);
                        providerService.modifyProvider(provider);
                    } else if ("update".equals(action)) {
                        providerService.modifyProvider(provider);
                    }
                    break;
                }
                case "product": {
                    Product product = OBJECT_MAPPER.convertValue(map, Product.class);
                    if ("add".equals(action)) {
                        product.setId(recordId.intValue());
                        product.setApprovalStatus(2);
                        productService.modifyProduct(product);
                    } else if ("update".equals(action)) {
                        productService.modifyProduct(product);
                    }
                    break;
                }
                case "bill": {
                    Bill bill = OBJECT_MAPPER.convertValue(map, Bill.class);
                    if ("add".equals(action)) {
                        bill.setId(recordId);
                        bill.setApprovalStatus(2);
                        billService.modifyBill(bill);
                    } else if ("update".equals(action)) {
                        billService.modifyBill(bill);
                    }
                    break;
                }
                case "order": {
                    Order order = OBJECT_MAPPER.convertValue(map, Order.class);
                    if ("add".equals(action)) {
                        order.setId(recordId);
                        order.setApprovalStatus(2);
                        orderService.modifyOrder(order);
                    } else if ("update".equals(action)) {
                        orderService.modifyOrder(order);
                    }
                    break;
                }
                case "news": {
                    News news = OBJECT_MAPPER.convertValue(map, News.class);
                    if ("add".equals(action)) {
                        news.setId(recordId);
                        news.setApprovalStatus(2);
                        newsService.modifyNews(news);
                    } else if ("update".equals(action)) {
                        newsService.modifyNews(news);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Failed to execute approved action: " + e.getMessage());
        }
    }

    /**
     * 新增审批：提前创建业务记录（草稿状态），供列表页可见
     */
    private Long createPendingRecord(Approval approval) {
        String detailJson = approval.getDetailJson();
        if (detailJson == null || detailJson.isEmpty()) return null;
        try {
            Map<String, Object> map = OBJECT_MAPPER.readValue(detailJson, Map.class);
            String module = approval.getModule();
            switch (module) {
                case "provider": {
                    Provider provider = OBJECT_MAPPER.convertValue(map, Provider.class);
                    provider.setApprovalStatus(1);
                    providerService.addProvider(provider);
                    return provider.getId();
                }
                case "bill": {
                    Bill bill = OBJECT_MAPPER.convertValue(map, Bill.class);
                    bill.setApprovalStatus(1);
                    billService.addBill(bill);
                    return bill.getId();
                }
                case "product": {
                    Product product = OBJECT_MAPPER.convertValue(map, Product.class);
                    product.setApprovalStatus(1);
                    productService.addProduct(product);
                    return product.getId().longValue();
                }
                case "order": {
                    Order order = OBJECT_MAPPER.convertValue(map, Order.class);
                    order.setApprovalStatus(1);
                    orderService.addOrder(order, null);
                    return order.getId();
                }
                case "user": {
                    User user = OBJECT_MAPPER.convertValue(map, User.class);
                    user.setStatus(3); // 已提交审批
                    userService.addUser(user);
                    return user.getId();
                }
                case "news": {
                    News news = OBJECT_MAPPER.convertValue(map, News.class);
                    news.setApprovalStatus(1);
                    newsService.addNews(news);
                    return news.getId();
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Failed to create pending record: " + e.getMessage());
        }
        return null;
    }

    /**
     * 新增审批驳回：删除提前创建的业务记录
     */
    private void deletePendingRecord(String module, Long recordId) {
        try {
            switch (module) {
                case "provider": providerService.deleteProvider(recordId); break;
                case "bill": billService.deleteBill(recordId); break;
                case "product": productService.deleteProduct(recordId.intValue()); break;
                case "order": orderService.deleteOrder(recordId); break;
                case "user": userService.deleteUser(recordId); break;
                case "news": newsService.deleteNews(recordId); break;
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to delete pending record for " + module + "#" + recordId + ": " + e.getMessage());
        }
    }

    /**
     * 提交申请时锁住业务记录状态为"待审批"
     */
    private void setBusinessPending(String module, Long recordId) {
        try {
            switch (module) {
                case "provider": providerService.submitForApproval(recordId); break;
                case "bill": billService.submitForApproval(recordId); break;
                case "product": productService.submitForApproval(recordId.intValue()); break;
                case "order": orderService.submitForApproval(recordId); break;
                case "user": userService.submitForApproval(recordId); break;
                case "news": newsService.submitForApproval(recordId); break;
                // category 无 approvalStatus 字段，暂不处理
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to set pending status for " + module + "#" + recordId + ": " + e.getMessage());
        }
    }

    /**
     * 同步业务记录的审批状态（approvalStatus 字段）
     */
    private void updateBusinessStatus(String module, Long recordId, int status) {
        try {
            switch (module) {
                case "provider": {
                    Provider provider = providerService.getProviderById(recordId);
                    if (provider != null) {
                        provider.setApprovalStatus(status);
                        providerService.modifyProvider(provider);
                    }
                    break;
                }
                case "bill": {
                    Bill bill = billService.getBillById(recordId);
                    if (bill != null) {
                        bill.setApprovalStatus(status);
                        billService.modifyBill(bill);
                    }
                    break;
                }
                case "product": {
                    Product product = productService.getProductById(recordId.intValue());
                    if (product != null) {
                        product.setApprovalStatus(status);
                        productService.modifyProduct(product);
                    }
                    break;
                }
                case "order": {
                    Order order = orderService.getOrderById(recordId);
                    if (order != null) {
                        order.setApprovalStatus(status);
                        orderService.modifyOrder(order);
                    }
                    break;
                }
                case "user": {
                    // User 用 status 字段而非 approvalStatus
                    if (status == 2) { // 通过 → 正常
                        userService.updateUserStatus(recordId, 1);
                    } else if (status == 3) { // 驳回 → 标记已驳回
                        userService.updateUserStatus(recordId, 2);
                    }
                    break;
                }
                case "news": {
                    News newsObj = newsService.getNewsById(recordId);
                    if (newsObj != null) {
                        newsObj.setApprovalStatus(status);
                        newsService.modifyNews(newsObj);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to sync business status for " + module + "#" + recordId + ": " + e.getMessage());
        }
    }
}