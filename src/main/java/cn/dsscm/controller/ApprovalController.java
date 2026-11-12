package cn.dsscm.controller;

import cn.dsscm.pojo.*;
import cn.dsscm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 审批流程控制器
 * 提供：提交申请、查看我的申请、待审批列表、审批通过/拒绝等功能。
 */
@Controller
@RequestMapping("/approval")
public class ApprovalController extends BaseController {

    @Autowired
    private ApprovalService approvalService;

    // 我的申请列表
    @GetMapping("/my")
    public String myApplications(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("list", approvalService.getMyApplications(loginUser.getId()));
        return "approval/my";
    }

    // 待审批列表（经理/管理员）
    @GetMapping("/pending")
    public String pendingList(HttpSession session, Model model) {
        if (!canApprove(session)) {
            return "redirect:/main";
        }
        model.addAttribute("list", approvalService.getPendingList());
        return "approval/pending";
    }

    // 提交申请
    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestParam String module,
                        @RequestParam String action,
                        @RequestParam(required = false) Long recordId,
                        @RequestParam String reason,
                        @RequestParam(required = false) String detailJson,
                        HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        Approval approval = new Approval();
        approval.setModule(module);
        approval.setAction(action);
        approval.setRecordId(recordId);
        approval.setApplicantId(user.getId());
        approval.setApplyReason(reason);
        approval.setDetailJson(detailJson);
        approval.setApplyDate(new Date());
        approvalService.apply(approval);
        return "success";
    }

    // 审批通过
    @PostMapping("/approve")
    @ResponseBody
    public String approve(@RequestParam Long id,
                          @RequestParam String comment,
                          HttpSession session) {
        if (!canApprove(session)) {
            return "forbidden";
        }
        User user = (User) session.getAttribute("loginUser");
        approvalService.approve(id, user.getId(), comment);
        return "ok";
    }

    // 审批拒绝
    @PostMapping("/reject")
    @ResponseBody
    public String reject(@RequestParam Long id,
                         @RequestParam String comment,
                         HttpSession session) {
        if (!canApprove(session)) {
            return "forbidden";
        }
        User user = (User) session.getAttribute("loginUser");
        approvalService.reject(id, user.getId(), comment);
        return "ok";
    }
}
