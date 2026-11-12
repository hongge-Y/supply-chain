package cn.dsscm.controller;

import cn.dsscm.pojo.Product;
import cn.dsscm.pojo.ProductCategory;
import cn.dsscm.pojo.User;
import cn.dsscm.service.ProductService;
import cn.dsscm.service.ProductCategoryService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 商品控制器
 * 提供商品的列表查询、新增、编辑、更新、删除、查看以及分类管理功能。
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 分页查询商品列表
     *
     * @param pageIndex         当前页码
     * @param pageSize          每页条数
     * @param name              商品名称（模糊查询，可选）
     * @param categoryLevel1Id  一级分类ID（可选）
     * @param categoryLevel2Id  二级分类ID（可选）
     * @param error             错误信息（可选）
     * @param model             视图模型
     * @return 商品列表页面
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Integer categoryLevel1Id,
                       @RequestParam(required = false) Integer categoryLevel2Id,
                       @RequestParam(required = false) String error,
                       Model model) {
        List<Product> products = productService.getProductList(name, categoryLevel1Id, categoryLevel2Id, pageIndex, pageSize);
        int totalCount = productService.getProductCount(name, categoryLevel1Id, categoryLevel2Id);
        PageUtil page = new PageUtil(pageIndex, pageSize, totalCount);
        List<ProductCategory> level1List = categoryService.getCategoriesByParentId(0);

        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("name", name);
        model.addAttribute("categoryLevel1Id", categoryLevel1Id);
        model.addAttribute("categoryLevel2Id", categoryLevel2Id);
        model.addAttribute("level1List", level1List);
        model.addAttribute("error", error);
        return "product/list";
    }

    /**
     * 跳转新增商品页面
     *
     * @param model 视图模型
     * @return 新增页面
     */
    @GetMapping("/add")
    public String toAddPage(Model model) {
        List<ProductCategory> level1List = categoryService.getCategoriesByParentId(0);
        model.addAttribute("level1List", level1List);
        return "product/add";
    }

    /**
     * 新增保存商品
     *
     * @param product          商品对象
     * @param session          当前会话
     * @param approvalStatus   审批状态
     * @return 重定向至列表页
     */
    @PostMapping("/save")
    public String save(Product product, HttpSession session,
                       @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        product.setCreatedBy(loginUser.getId());
        product.setApprovalStatus(approvalStatus);
        try {
            productService.addProduct(product);
            if (approvalStatus == 1) {
                productService.submitForApproval(product.getId());
            }
        } catch (IllegalArgumentException e) {
            return "redirect:/product/list?error=" + e.getMessage();
        }
        return "redirect:/product/list";
    }

    /**
     * 跳转编辑商品页面
     *
     * @param id      商品ID
     * @param session 当前会话
     * @param model   视图模型
     * @return 编辑页面，若不存在则重定向至列表页
     */
    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable Integer id, HttpSession session, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/product/list?error=notfound";
        }
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/list?error=noauth";
        }
        List<ProductCategory> level1List = categoryService.getCategoriesByParentId(0);
        model.addAttribute("product", product);
        model.addAttribute("level1List", level1List);
        return "product/edit";
    }

    /**
     * 更新保存商品
     *
     * @param product          商品对象
     * @param session          当前会话
     * @param approvalStatus   审批状态
     * @return 重定向至列表页
     */
    @PostMapping("/update")
    public String update(Product product, HttpSession session,
                         @RequestParam(defaultValue = "1") Integer approvalStatus) {
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        product.setModifyBy(loginUser.getId());
        product.setApprovalStatus(approvalStatus);
        try {
            productService.modifyProduct(product);
            if (approvalStatus == 1) {
                productService.submitForApproval(product.getId());
            }
        } catch (IllegalArgumentException e) {
            return "redirect:/product/list?error=" + e.getMessage();
        }
        return "redirect:/product/list";
    }

    /**
     * 删除商品
     *
     * @param id      商品ID
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, HttpSession session) {
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/list?error=noauth";
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/product/list?error=notfound";
        }
        if (!canDelete(session, product.getCreatedBy(), product.getApprovalStatus())) {
            return "redirect:/product/list?error=noauth";
        }
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }

    /**
     * 查看商品详情（含分类名称）
     *
     * @param id    商品ID
     * @param model 视图模型
     * @return 查看页面
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        // 查询分类名称
        if (product != null) {
            if (product.getCategoryLevel1Id() != null) {
                ProductCategory c1 = categoryService.getCategoryById(product.getCategoryLevel1Id());
                model.addAttribute("categoryName1", c1 != null ? c1.getName() : "");
            }
            if (product.getCategoryLevel2Id() != null) {
                ProductCategory c2 = categoryService.getCategoryById(product.getCategoryLevel2Id());
                model.addAttribute("categoryName2", c2 != null ? c2.getName() : "");
            }
            if (product.getCategoryLevel3Id() != null) {
                ProductCategory c3 = categoryService.getCategoryById(product.getCategoryLevel3Id());
                model.addAttribute("categoryName3", c3 != null ? c3.getName() : "");
            }
        }
        return "product/view";
    }

    // ========== 分类管理 ==========

    /**
     * 按父级ID查询商品分类列表
     *
     * @param parentId 父级分类ID
     * @param model    视图模型
     * @return 分类列表页面
     */
    @GetMapping("/category/list")
    public String categoryList(@RequestParam(defaultValue = "0") Integer parentId, Model model) {
        List<ProductCategory> categories = categoryService.getCategoriesByParentId(parentId);
        model.addAttribute("categories", categories);
        model.addAttribute("parentId", parentId);
        if (parentId != 0) {
            ProductCategory parent = categoryService.getCategoryById(parentId);
            model.addAttribute("parentLevel", parent.getType());
            model.addAttribute("parentName", parent.getName());
        }
        return "product/category_list";
    }

    /**
     * 跳转新增分类页面
     *
     * @param parentId 父级分类ID
     * @param model    视图模型
     * @return 新增分类页面
     */
    @GetMapping("/category/add")
    public String toAddCategory(@RequestParam(defaultValue = "0") Integer parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "product/category_add";
    }

    /**
     * 新增保存分类
     *
     * @param category 分类对象
     * @param session  当前会话
     * @return 重定向至分类列表页
     */
    @PostMapping("/category/save")
    public String saveCategory(ProductCategory category, HttpSession session) {
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/category/list?parentId=" + category.getParentId() + "&error=noauth";
        }
        categoryService.addCategory(category);
        return "redirect:/product/category/list?parentId=" + category.getParentId();
    }

    /**
     * 跳转编辑分类页面
     *
     * @param id    分类ID
     * @param model 视图模型
     * @return 编辑分类页面
     */
    @GetMapping("/category/edit/{id}")
    public String toEditCategory(@PathVariable Integer id, Model model) {
        ProductCategory category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "product/category_edit";
    }

    /**
     * 更新保存分类
     *
     * @param category 分类对象
     * @param session  当前会话
     * @return 重定向至分类列表页
     */
    @PostMapping("/category/update")
    public String updateCategory(ProductCategory category, HttpSession session) {
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/category/list?parentId=" + category.getParentId() + "&error=noauth";
        }
        categoryService.modifyCategory(category);
        return "redirect:/product/category/list?parentId=" + category.getParentId();
    }

    /**
     * 删除分类
     *
     * @param id       分类ID
     * @param parentId 父级分类ID，用于重定向回正确的分类列表
     * @param session  当前会话
     * @return 重定向至分类列表页
     */
    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, @RequestParam Integer parentId, HttpSession session) {
        if (!hasDirectPermission(session, "product")) {
            return "redirect:/product/category/list?parentId=" + parentId + "&error=noauth";
        }
        categoryService.deleteCategory(id);
        return "redirect:/product/category/list?parentId=" + parentId;
    }
}
