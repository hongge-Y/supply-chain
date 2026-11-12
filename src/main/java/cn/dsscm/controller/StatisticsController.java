package cn.dsscm.controller;

import cn.dsscm.service.OrderService;
import cn.dsscm.service.BillService;
import cn.dsscm.service.ProductService;
import cn.dsscm.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 经营统计控制器
 * 提供系统运营数据的概览展示（订单数、商品数等）。
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BillService billService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProviderService providerService;

    /**
     * 统计概览首页，展示各模块的统计数据
     *
     * @param model 视图模型
     * @return 统计首页
     */
    @GetMapping("/index")
    public String index(Model model) {
        int totalOrders = orderService.getOrderCount(null, null);
        int totalBills = billService.getBillCount(null, null, null);
        int totalProducts = productService.getProductCount(null, null, null);
        int totalProviders = providerService.getProviderCount(null, null);

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalBills", totalBills);
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalProviders", totalProviders);
        return "statistics/index";
    }
}