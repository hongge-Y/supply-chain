package cn.dsscm.controller;

import cn.dsscm.pojo.*;
import cn.dsscm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 审批工作台控制器
 * 经理/管理员专用统一审批入口，同时处理：
 * 1. 业务单据的提交审批（草稿→待审批→通过/驳回）
 * 2. 员工申请的修改/删除审批（tb_approval表）
 */
@Controller
@RequestMapping("/approval/workbench")
public class ApprovalWorkbenchController extends BaseController {

    @Autowired private ProviderService providerService;
    @Autowired private BillService billService;
    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;
    @Autowired private ApprovalService approvalService;
    @Autowired private UserService userService;

    /**
     * 审批工作台首页
     */
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        if (!canApprove(session)) {
            return "redirect:/main";
        }

        // 所有待审批数据统一来自 tb_approval 表
        List<Approval> pendingApprovals = approvalService.getPendingList();

        // 按模块分组
        List<Approval> appProviders = new ArrayList<>();
        List<Approval> appBills = new ArrayList<>();
        List<Approval> appProducts = new ArrayList<>();
        List<Approval> appOrders = new ArrayList<>();
        List<Approval> appUsers = new ArrayList<>();
        List<Approval> appNews = new ArrayList<>();

        for (Approval a : pendingApprovals) {
            switch (a.getModule()) {
                case "provider": appProviders.add(a); break;
                case "bill": appBills.add(a); break;
                case "product": appProducts.add(a); break;
                case "order": appOrders.add(a); break;
                case "user": appUsers.add(a); break;
                case "news": appNews.add(a); break;
            }
        }

        model.addAttribute("appProviders", appProviders);
        model.addAttribute("appBills", appBills);
        model.addAttribute("appProducts", appProducts);
        model.addAttribute("appOrders", appOrders);
        model.addAttribute("appUsers", appUsers);
        model.addAttribute("appNews", appNews);
        model.addAttribute("pendingApprovals", pendingApprovals);

        // "全部"列表
        List<Map<String, Object>> pendingAll = new ArrayList<>();
        for (Approval a : pendingApprovals) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("module", a.getModule());
            item.put("moduleName", a.getModuleName());
            item.put("id", a.getId());
            item.put("name", a.getActionName() + "申请");
            item.put("code", a.getApplicantName());
            item.put("desc", a.getApplyReason());
            item.put("detailJson", a.getDetailJson());
            item.put("action", a.getAction());
            pendingAll.add(item);
        }

        model.addAttribute("pendingAll", pendingAll);

        int totalPending = pendingApprovals.size();
        model.addAttribute("pendingApprovalCount", totalPending);
        session.setAttribute("pendingApprovalCount", totalPending);

        return "approval/workbench";
    }

    /**
     * 提交审批（草稿 → 待审批）
     */
    @PostMapping("/submit")
    @ResponseBody
    public String submitForApproval(@RequestParam String module, @RequestParam Long id) {
        try {
            switch (module) {
                case "provider": providerService.submitForApproval(id); break;
                case "bill": billService.submitForApproval(id); break;
                case "product": productService.submitForApproval(Integer.valueOf(id.intValue())); break;
                case "order": orderService.submitForApproval(id); break;
                case "user": userService.submitForApproval(id); break;
                default: return "unknown_module";
            }
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 审批通过
     */
    @PostMapping("/approve")
    @ResponseBody
    public String approve(@RequestParam String module, @RequestParam Long id,
                          @RequestParam(defaultValue = "") String comment,
                          @RequestParam(defaultValue = "submit") String type,
                          HttpSession session) {
        if (!canApprove(session)) return "forbidden";
        try {
            if ("apply".equals(type)) {
                // tb_approval 申请审批通过
                User user = (User) session.getAttribute("loginUser");
                approvalService.approve(id, user.getId(), comment);
                return "ok";
            }
            switch (module) {
                case "provider": providerService.approve(id); break;
                case "bill": billService.approve(id); break;
                case "product": productService.approve(Integer.valueOf(id.intValue())); break;
                case "order": orderService.approve(id); break;
                case "user": userService.updateUserStatus(id, 1); break;
                default: return "unknown_module";
            }
            return "ok";
        } catch (Exception e) { return e.getMessage(); }
    }

    /**
     * 审批驳回
     */
    @PostMapping("/reject")
    @ResponseBody
    public String reject(@RequestParam String module, @RequestParam Long id,
                         @RequestParam(defaultValue = "") String comment,
                         @RequestParam(defaultValue = "submit") String type,
                         HttpSession session) {
        if (!canApprove(session)) return "forbidden";
        try {
            if ("apply".equals(type)) {
                // tb_approval 申请审批驳回
                User user = (User) session.getAttribute("loginUser");
                approvalService.reject(id, user.getId(), comment);
                return "ok";
            }
            switch (module) {
                case "provider": providerService.reject(id); break;
                case "bill": billService.reject(id); break;
                case "product": productService.reject(Integer.valueOf(id.intValue())); break;
                case "order": orderService.reject(id); break;
                case "user": userService.updateUserStatus(id, 0); break;
                default: return "unknown_module";
            }
            return "ok";
        } catch (Exception e) { return e.getMessage(); }
    }
}
