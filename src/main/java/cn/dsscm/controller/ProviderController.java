package cn.dsscm.controller;

import cn.dsscm.pojo.Provider;
import cn.dsscm.pojo.User;
import cn.dsscm.service.ProviderService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 供应商控制器
 * 提供供应商的列表查询、新增、编辑、更新、删除和查看功能。
 */
@Controller
@RequestMapping("/provider")
public class ProviderController extends BaseController {

    @Autowired
    private ProviderService providerService;

    /**
     * 分页查询供应商列表
     *
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @param proCode   供应商编码（模糊查询，可选）
     * @param proName   供应商名称（模糊查询，可选）
     * @param error     错误信息（可选）
     * @param model     视图模型
     * @return 供应商列表页面
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String proCode,
                       @RequestParam(required = false) String proName,
                       @RequestParam(required = false) String error,
                       Model model) {
        List<Provider> providers = providerService.getProviderList(proCode, proName, pageIndex, pageSize);
        int totalCount = providerService.getProviderCount(proCode, proName);
        PageUtil page = new PageUtil(pageIndex, pageSize, totalCount);

        model.addAttribute("providers", providers);
        model.addAttribute("page", page);
        model.addAttribute("proCode", proCode);
        model.addAttribute("proName", proName);
        model.addAttribute("error", error);
        return "provider/list";
    }

    /**
     * 跳转新增供应商页面
     *
     * @return 新增页面
     */
    @GetMapping("/add")
    public String toAddPage() {
        return "provider/add";
    }

    /**
     * 新增保存供应商
     *
     * @param provider        供应商对象
     * @param session         当前会话
     * @param approvalStatus  审批状态
     * @return 重定向至列表页
     */
    @PostMapping("/save")
    public String save(Provider provider, HttpSession session,
                       @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "provider")) {
            return "redirect:/provider/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        provider.setCreatedBy(loginUser.getId());
        provider.setApprovalStatus(approvalStatus);
        providerService.addProvider(provider);
        if (approvalStatus == 1) {
            providerService.submitForApproval(provider.getId());
        }
        return "redirect:/provider/list";
    }

    /**
     * 跳转编辑供应商页面
     *
     * @param id      供应商ID
     * @param session 当前会话
     * @param model   视图模型
     * @return 编辑页面，若不存在则重定向至列表页
     */
    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, HttpSession session, Model model) {
        Provider provider = providerService.getProviderById(id);
        if (provider == null) {
            return "redirect:/provider/list?error=notfound";
        }
        // 权限校验：有模块访问权限即可进入编辑页
        if (!hasDirectPermission(session, "provider")) {
            return "redirect:/provider/list?error=noauth";
        }
        model.addAttribute("provider", provider);
        return "provider/edit";
    }

    /**
     * 更新保存供应商
     *
     * @param provider        供应商对象
     * @param session         当前会话
     * @param approvalStatus  审批状态
     * @return 重定向至列表页
     */
    @PostMapping("/update")
    public String update(Provider provider, HttpSession session,
                         @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "provider")) {
            return "redirect:/provider/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        provider.setModifyBy(loginUser.getId());
        provider.setApprovalStatus(approvalStatus);
        providerService.modifyProvider(provider);
        if (approvalStatus == 1) {
            providerService.submitForApproval(provider.getId());
        }
        return "redirect:/provider/list";
    }

    /**
     * 删除供应商
     *
     * @param id      供应商ID
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!hasDirectPermission(session, "provider")) {
            return "redirect:/provider/list?error=noauth";
        }
        Provider provider = providerService.getProviderById(id);
        if (provider == null) {
            return "redirect:/provider/list?error=notfound";
        }
        // 普通员工只能删自己创建的草稿；经理可删任何
        if (!canDelete(session, provider.getCreatedBy(), provider.getApprovalStatus())) {
            return "redirect:/provider/list?error=noauth";
        }
        providerService.deleteProvider(id);
        return "redirect:/provider/list";
    }

    /**
     * 查看供应商详情
     *
     * @param id    供应商ID
     * @param model 视图模型
     * @return 查看页面
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Provider provider = providerService.getProviderById(id);
        model.addAttribute("provider", provider);
        return "provider/view";
    }
}
