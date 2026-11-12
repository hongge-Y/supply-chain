package cn.dsscm.interceptor;

import cn.dsscm.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录权限拦截器
 * 用途：对除了登录相关请求外的所有 URL 进行登录校验，
 *       若用户未登录（session 中无 loginUser），则强制重定向至登录页。
 *
 * 注意：此拦截器需在 Spring MVC 配置中注册，并排除 "/login"、"/dologin"、"/index.jsp"、"/statics/**" 等路径。
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在 Controller 方法执行前调用
     * @return true 表示放行，false 表示拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 获取当前会话
        HttpSession session = request.getSession();
        // 检查是否已登录：判断 session 中是否存在 loginUser 属性
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {
            // 已登录，放行
            return true;
        }

        // 未登录，重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }

    /**
     * 在 Controller 方法执行后，视图渲染前调用（此处无需处理）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // 可在此处添加额外逻辑，例如向视图注入登录用户信息
    }

    /**
     * 在请求处理完成后调用（视图已渲染）
     * 可用于资源清理等（此处无需处理）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 空实现
    }
}