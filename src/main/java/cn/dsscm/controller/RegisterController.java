package cn.dsscm.controller;

import cn.dsscm.pojo.User;
import cn.dsscm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 用户注册控制器
 * 提供未登录用户自助注册功能。注册成功后跳转至登录页。
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 跳转注册页面
     *
     * @return 注册页面
     */
    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    /**
     * 处理用户注册提交
     *
     * @param userCode     用户编码
     * @param userName     真实姓名
     * @param userPassword 密码
     * @param gender       性别
     * @param email        邮箱（可选）
     * @param phone        手机号（可选）
     * @param model        视图模型
     * @return 注册成功重定向至登录页，失败返回注册页面
     */
    @PostMapping("/doregister")
    public String doRegister(@RequestParam String userCode,
                             @RequestParam String userName,
                             @RequestParam String userPassword,
                             @RequestParam Integer gender,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String phone,
                             Model model) {
        // 检查用户编码是否已存在
        User existUser = userService.getUserByCode(userCode);
        if (existUser != null) {
            model.addAttribute("error", "用户编码已被注册，请更换");
            return "register";
        }

        // 构建新用户对象
        User newUser = new User();
        newUser.setUserCode(userCode);
        newUser.setUserName(userName);
        newUser.setUserPassword(userPassword);
        newUser.setGender(gender);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setCreationDate(new Date());
        newUser.setCreatedBy(0L); // 表示系统自动注册
        newUser.setUserRole(3);   // 默认角色为"普通员工"（对应 tb_role.id=3，须与 seed 数据一致）
        newUser.setStatus(1);     // 直接激活

        userService.addUser(newUser);
        return "redirect:/login?registered=1"; // 注册成功，跳转登录页
    }
}
