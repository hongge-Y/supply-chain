package cn.dsscm.config;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * 统一捕获并处理控制器层抛出的异常，返回友好的错误提示页面。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数类型不匹配（如数字字段输入了字符串）
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ModelAndView handleTypeMismatch(TypeMismatchException ex, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error"); // 需要创建 /WEB-INF/jsp/error.jsp
        mv.addObject("message", "输入格式不正确：" + ex.getPropertyName() + " 应为 " + ex.getRequiredType().getSimpleName() + " 类型");
        mv.addObject("url", request.getHeader("Referer")); // 可以返回上一页
        return mv;
    }

    /**
     * 处理其他异常（可选，避免直接500）
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", "系统繁忙，请稍后再试");
        return mv;
    }
}