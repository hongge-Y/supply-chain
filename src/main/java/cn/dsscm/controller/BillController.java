package cn.dsscm.controller;

import cn.dsscm.pojo.Bill;
import cn.dsscm.pojo.Provider;
import cn.dsscm.pojo.User;
import cn.dsscm.service.BillService;
import cn.dsscm.service.ProviderService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 采购订单控制器
 * 提供采购订单的列表查询、新增、编辑、更新、删除和查看功能。
 */
@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {

    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    /**
     * 分页查询采购订单列表
     *
     * @param pageIndex    当前页码
     * @param pageSize     每页条数
     * @param productName  商品名称（模糊查询，可选）
     * @param providerId   供应商ID（可选）
     * @param isPayment    是否支付（可选）
     * @param error        错误信息（可选）
     * @param model        视图模型
     * @return 采购订单列表页面
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String productName,
                       @RequestParam(required = false) Integer providerId,
                       @RequestParam(required = false) Integer isPayment,
                       @RequestParam(required = false) String error,
                       Model model) {
        List<Bill> bills = billService.getBillList(productName, providerId, isPayment, pageIndex, pageSize);
        int totalCount = billService.getBillCount(productName, providerId, isPayment);
        PageUtil page = new PageUtil(pageIndex, pageSize, totalCount);
        List<Provider> providers = providerService.getProviderList(null, null, 1, 999);

        model.addAttribute("bills", bills);
        model.addAttribute("page", page);
        model.addAttribute("providers", providers);
        model.addAttribute("productName", productName);
        model.addAttribute("providerId", providerId);
        model.addAttribute("isPayment", isPayment);
        model.addAttribute("error", error);
        return "bill/list";
    }

    /**
     * 跳转新增采购订单页面
     *
     * @param model 视图模型
     * @return 新增页面
     */
    @GetMapping("/add")
    public String toAddPage(Model model) {
        List<Provider> providers = providerService.getProviderList(null, null, 1, 999);
        model.addAttribute("providers", providers);
        return "bill/add";
    }

    /**
     * 新增保存采购订单
     *
     * @param bill            采购订单对象
     * @param session         当前会话
     * @param approvalStatus  审批状态（默认提交审批）
     * @return 重定向至列表页
     */
    @PostMapping("/save")
    public String save(Bill bill, HttpSession session,
                       @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "bill")) {
            return "redirect:/bill/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        bill.setCreatedBy(loginUser.getId());
        bill.setApprovalStatus(approvalStatus);
        billService.addBill(bill);
        if (approvalStatus == 1) {
            billService.submitForApproval(bill.getId());
        }
        return "redirect:/bill/list";
    }

    /**
     * 跳转编辑采购订单页面
     *
     * @param id      订单ID
     * @param session 当前会话
     * @param model   视图模型
     * @return 编辑页面，若不存在则重定向至列表页
     */
    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, HttpSession session, Model model) {
        Bill bill = billService.getBillById(id);
        if (bill == null) {
            return "redirect:/bill/list?error=notfound";
        }
        if (!hasDirectPermission(session, "bill")) {
            return "redirect:/bill/list?error=noauth";
        }
        List<Provider> providers = providerService.getProviderList(null, null, 1, 999);
        model.addAttribute("bill", bill);
        model.addAttribute("providers", providers);
        return "bill/edit";
    }

    /**
     * 更新保存采购订单
     *
     * @param bill            采购订单对象
     * @param session         当前会话
     * @param approvalStatus  审批状态
     * @return 重定向至列表页
     */
    @PostMapping("/update")
    public String update(Bill bill, HttpSession session,
                         @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "bill")) {
            return "redirect:/bill/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        bill.setModifyBy(loginUser.getId());
        bill.setApprovalStatus(approvalStatus);
        billService.modifyBill(bill);
        if (approvalStatus == 1) {
            billService.submitForApproval(bill.getId());
        }
        return "redirect:/bill/list";
    }

    /**
     * 删除采购订单
     *
     * @param id      订单ID
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!hasDirectPermission(session, "bill")) {
            return "redirect:/bill/list?error=noauth";
        }
        Bill bill = billService.getBillById(id);
        if (bill == null) {
            return "redirect:/bill/list?error=notfound";
        }
        if (!canDelete(session, bill.getCreatedBy(), bill.getApprovalStatus())) {
            return "redirect:/bill/list?error=noauth";
        }
        billService.deleteBill(id);
        return "redirect:/bill/list";
    }

    /**
     * 查看采购订单详情
     *
     * @param id    订单ID
     * @param model 视图模型
     * @return 查看页面
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill", bill);
        return "bill/view";
    }
}
