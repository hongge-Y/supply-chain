package cn.dsscm.config;

import cn.dsscm.interceptor.LoginInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC 配置类
 * 负责：视图解析器、静态资源映射、拦截器注册、格式化器注册。
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"cn.dsscm.controller", "cn.dsscm.interceptor"})
public class SpringMVCConfig implements WebMvcConfigurer {

    /**
     * 配置 JSP 视图解析器
     *
     * @return InternalResourceViewResolver
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    /**
     * 配置静态资源映射
     *
     * @param registry 资源处理器注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("/statics/");
    }

    /**
     * 注册拦截器（除登录、注册、静态资源外全部拦截）
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",           // 登录页
                        "/dologin",        // 登录请求
                        "/register",       // 注册页
                        "/doregister",     // 注册请求
                        "/index.jsp",
                        "/statics/**"
                );
    }

    /**
     * 注册日期格式化器
     *
     * @param registry 格式化器注册表
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }
}