package cn.dsscm.controller;

import cn.dsscm.pojo.News;
import cn.dsscm.pojo.User;
import cn.dsscm.service.NewsService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 新闻公告控制器
 * 提供新闻公告的列表查询、新增、编辑、更新、删除和查看功能。
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    /**
     * 分页查询新闻公告列表
     *
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @param title     标题（模糊查询，可选）
     * @param error     错误信息（可选）
     * @param session   当前会话
     * @param model     视图模型
     * @return 新闻列表页面
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) String error,
                       HttpSession session,
                       Model model) {
        // 新注册用户(role=3)不可见新闻模块
        if (!canAccessNews(session)) {
            return "redirect:/main";
        }
        List<News> newsList = newsService.getNewsList(title, pageIndex, pageSize);
        int totalCount = newsService.getNewsCount(title);
        PageUtil page = new PageUtil(pageIndex, pageSize, totalCount);

        model.addAttribute("newsList", newsList);
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("error", error);

        // 是否拥有发文权限（销售部、经理、管理员），供视图判断显示新增/编辑/删除按钮
        model.addAttribute("canPublish", canPublishNews(session));

        return "news/list";
    }

    /**
     * 跳转新增新闻页面
     *
     * @param session 当前会话（需校验发文权限）
     * @return 新增页面
     */
    @GetMapping("/add")
    public String toAddPage(HttpSession session) {
        // 仅销售部、经理、管理员可新增
        if (!canPublishNews(session)) {
            return "redirect:/news/list?error=noauth";
        }
        return "news/add";
    }

    /**
     * 新增保存新闻
     *
     * @param news    新闻对象
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @PostMapping("/save")
    public String save(News news, HttpSession session) {
        // 仅销售部、经理、管理员可新增
        if (!canPublishNews(session)) {
            return "redirect:/news/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        news.setCreatedBy(loginUser.getId());
        newsService.addNews(news);
        return "redirect:/news/list";
    }

    /**
     * 跳转编辑新闻页面
     *
     * @param id      新闻ID
     * @param session 当前会话
     * @param model   视图模型
     * @return 编辑页面
     */
    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, HttpSession session, Model model) {
        // 仅销售部、经理、管理员可编辑
        if (!canPublishNews(session)) {
            return "redirect:/news/list?error=noauth";
        }
        News news = newsService.getNewsById(id);
        if (news == null) {
            return "redirect:/news/list?error=notfound";
        }
        model.addAttribute("news", news);
        return "news/edit";
    }

    /**
     * 更新保存新闻
     *
     * @param news    新闻对象
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @PostMapping("/update")
    public String update(News news, HttpSession session) {
        // 仅销售部、经理、管理员可编辑
        if (!canPublishNews(session)) {
            return "redirect:/news/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        news.setModifyBy(loginUser.getId());
        newsService.modifyNews(news);
        return "redirect:/news/list";
    }

    /**
     * 删除新闻
     *
     * @param id      新闻ID
     * @param session 当前会话
     * @return 重定向至列表页
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        // 仅销售部、经理、管理员可删除
        if (!canPublishNews(session)) {
            return "redirect:/news/list?error=noauth";
        }
        newsService.deleteNews(id);
        return "redirect:/news/list";
    }

    /**
     * 查看新闻详情
     *
     * @param id      新闻ID
     * @param session 当前会话
     * @param model   视图模型
     * @return 查看页面
     */
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, HttpSession session, Model model) {
        // 新注册用户不可查看
        if (!canAccessNews(session)) {
            return "redirect:/main";
        }
        News news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        return "news/view";
    }
}
