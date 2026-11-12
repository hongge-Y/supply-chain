package cn.dsscm.controller;

import cn.dsscm.pojo.Order;
import cn.dsscm.pojo.OrderDetail;
import cn.dsscm.pojo.User;
import cn.dsscm.service.OrderService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 销售订单控制器
 * 支持添加、修改、查看、删除及审批提交流程。
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询销售订单列表
     *
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @param userName  顾客姓名（模糊查询，可选）
     * @param status    订单状态（可选）
     * @param error     错误信息（可选）
     * @param model     视图模型
     * @return 销售订单列表页面
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String userName,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) String error,
                       Model model) {
        List<Order> orders = orderService.getOrderList(userName, status, pageIndex, pageSize);
        int totalCount = orderService.getOrderCount(userName, status);
        PageUtil page = new PageUtil(pageIndex, pageSize, totalCount);

        model.addAttribute("orders", orders);
        model.addAttribute("page", page);
        model.addAttribute("userName", userName);
        model.addAttribute("status", status);
        model.addAttribute("error", error);
        return "order/list";
    }

    /**
     * 跳转新增销售订单页面
     *
     * @return 新增页面
     */
    @GetMapping("/add")
    public String toAddPage() {
        return "order/add";
    }

    /**
     * 新增保存销售订单（含明细）
     *
     * @param order           订单对象
     * @param details         订单明细列表（可选）
     * @param session         当前会话
     * @param approvalStatus  审批状态
     * @return 重定向至列表页
     */
    @PostMapping("/save")
    public String save(Order order, @RequestParam(required = false) List<OrderDetail> details, HttpSession session,
                       @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "order")) {
            return "redirect:/order/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        order.setCreatedBy(loginUser.getId());
        order.setApprovalStatus(approvalStatus);
        orderService.addOrder(order, details);
        if (approvalStatus == 1) {
            orderService.submitForApproval(order.getId());
        }
        return "redirect:/order/list";
    }

    /** 跳转编辑页 */
    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, HttpSession session, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/order/list?error=notfound";
        }
        if (!hasDirectPermission(session, "order")) {
            return "redirect:/order/list?error=noauth";
        }
        model.addAttribute("order", order);
        return "order/edit";
    }

    /** 更新订单 */
    @PostMapping("/update")
    public String update(Order order, HttpSession session,
                         @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "order")) {
            return "redirect:/order/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        order.setModifyBy(loginUser.getId());
        order.setApprovalStatus(approvalStatus);
        orderService.modifyOrder(order);
        if (approvalStatus == 1) {
            orderService.submitForApproval(order.getId());
        }
        return "redirect:/order/list";
    }

    /**
     * 删除销售订单（级联删除明细）
     *
     * @param id      订单ID
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!hasDirectPermission(session, "order")) {
            return "redirect:/order/list?error=noauth";
        }
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/order/list?error=notfound";
        }
        if (!canDelete(session, order.getCreatedBy(), order.getApprovalStatus())) {
            return "redirect:/order/list?error=noauth";
        }
        orderService.deleteOrder(id);
        return "redirect:/order/list";
    }

    /**
     * 查看销售订单详情（含明细）
     *
     * @param id    订单ID
     * @param model 视图模型
     * @return 查看页面
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        List<OrderDetail> details = orderService.getOrderDetailsByOrderId(id.intValue());
        model.addAttribute("order", order);
        model.addAttribute("details", details);
        return "order/view";
    }
}
