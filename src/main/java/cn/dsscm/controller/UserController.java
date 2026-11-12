package cn.dsscm.controller;

import cn.dsscm.pojo.Role;
import cn.dsscm.pojo.User;
import cn.dsscm.service.RoleService;
import cn.dsscm.service.UserService;
import cn.dsscm.tools.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int pageIndex,
                       @RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(required = false) String userName,
                       @RequestParam(required = false) Integer roleId,
                       @RequestParam(required = false) String error,
                       HttpSession session,
                       Model model) {
        if (!canAccessUser(session)) {
            return "redirect:/main";
        }
        List<User> users = userService.getUserList(userName, roleId, pageIndex, pageSize);
        int totalCount = userService.getUserCount(userName, roleId);
        PageUtil page = new PageUtil(pageIndex, pageSize, totalCount);
        List<Role> roles = roleService.getAllRoles();

        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("roles", roles);
        model.addAttribute("userName", userName);
        model.addAttribute("roleId", roleId);
        model.addAttribute("error", error);
        return "user/list";
    }

    @GetMapping("/add")
    public String toAddPage(HttpSession session, Model model) {
        if (!canAccessUser(session)) {
            return "redirect:/main";
        }
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "user/add";
    }

    @PostMapping("/save")
    public String save(User user, HttpSession session) {
        if (!canAccessUser(session)) {
            return "redirect:/user/list?error=noauth";
        }
        User loginUser = (User) session.getAttribute("loginUser");
        user.setCreatedBy(loginUser.getId());
        // 所有人都保存为待审核草稿，经理/管理员在审批工作台激活
        user.setStatus(0);
        userService.addUser(user);
        if (isAdminOrManager(session)) {
            // 经理/管理员直接激活
            userService.updateUserStatus(user.getId(), 1);
        }
        return "redirect:/user/list";
    }

    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, HttpSession session, Model model) {
        if (!canAccessUser(session)) {
            return "redirect:/main";
        }
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/user/list?error=notfound";
        }
        // 所有人均可进入编辑页（普通员工保存后走审批）
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user/edit";
    }

    @PostMapping("/update")
    public String update(User user, HttpSession session) {
        if (!canAccessUser(session)) {
            return "redirect:/user/list?error=noauth";
        }
        User oldUser = userService.getUserById(user.getId());
        if (oldUser == null) {
            return "redirect:/user/list?error=notfound";
        }
        // 普通员工保存后状态重置为待审核，走审批流
        User loginUser = (User) session.getAttribute("loginUser");
        user.setModifyBy(loginUser.getId());
        if (!isAdminOrManager(session)) {
            user.setStatus(0); // 重置为待审核
        }
        userService.modifyUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!canAccessUser(session)) {
            return "redirect:/user/list?error=noauth";
        }
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/user/list?error=notfound";
        }
        // 人事部不能直接删除已生效用户
        if (!isAdminOrManager(session) && user.getStatus() == 1) {
            return "redirect:/user/list?error=needapproval";
        }
        userService.deleteUser(id);
        return "redirect:/user/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, HttpSession session, Model model) {
        if (!canAccessUser(session)) {
            return "redirect:/main";
        }
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user/view";
    }
}
