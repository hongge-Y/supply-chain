package cn.dsscm.controller;

import cn.dsscm.pojo.News;
import cn.dsscm.pojo.Role;
import cn.dsscm.pojo.User;
import cn.dsscm.service.NewsService;
import cn.dsscm.service.RoleService;
import cn.dsscm.service.UserService;
import cn.dsscm.service.OrderService;
import cn.dsscm.service.BillService;
import cn.dsscm.service.ProviderService;
import cn.dsscm.service.ProductService;
import cn.dsscm.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户认证与主界面控制器
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BillService billService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ApprovalService approvalService;

    /**
     * 跳转至登录页面
     */
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 处理登录请求
     * @param userCode     用户编码
     * @param userPassword 密码
     * @param session      用于保存登录用户及角色信息
     */
    @PostMapping("/dologin")
    public String doLogin(@RequestParam String userCode,
                          @RequestParam String userPassword,
                          HttpSession session) {
        User user = userService.login(userCode, userPassword);
        if (user != null) {
            session.setAttribute("loginUser", user);
            // 查询并存储角色编码
            Role role = roleService.getRoleById((long) user.getUserRole());
            if (role != null) {
                session.setAttribute("roleCode", role.getRoleCode());
            }
            return "redirect:/main";
        } else {
            return "redirect:/login?error=1";
        }
    }

    /**
     * 跳转到系统主界面，同时加载最新公告
     */
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        // 获取最新5条新闻作为首页公告
        List<News> latestNews = newsService.getNewsList(null, 1, 5);
        model.addAttribute("latestNews", latestNews);

        // 获取系统统计数据用于首页展示
        try {
            int totalOrdersCount = orderService.getOrderCount(null, null);
            int totalBillsCount = billService.getBillCount(null, null, null);
            int totalProvidersCount = providerService.getProviderCount(null, null);
            int totalUsersCount = userService.getUserCount(null, null);
            model.addAttribute("totalOrdersCount", totalOrdersCount);
            model.addAttribute("totalBillsCount", totalBillsCount);
            model.addAttribute("totalProvidersCount", totalProvidersCount);
            model.addAttribute("totalUsersCount", totalUsersCount);

            // 待审批数量统一来自 tb_approval
            int totalPending = approvalService.getPendingList().size();
            session.setAttribute("pendingApprovalCount", totalPending);
        } catch (Exception e) {
            // 如果统计查询出错，忽略即可，首页仍可正常显示
        }

        return "main";
    }

    /**
     * 用户注销
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
