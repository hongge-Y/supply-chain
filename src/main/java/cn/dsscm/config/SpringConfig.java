package cn.dsscm.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Spring 根容器配置类
 * 负责：数据源、事务管理、Service 层、工具类等
 * 加载顺序：在 ContextLoaderListener 中初始化
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = {"cn.dsscm.service", "cn.dsscm.tools", "cn.dsscm.config"})
@Import(MyBatisConfig.class)   // 显式导入 MyBatis 配置
public class SpringConfig {


    // 从 jdbc.properties 中注入数据库连接参数
    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.initialSize:5}")   // 默认值 5
    private int initialSize;

    @Value("${jdbc.maxActive:20}")    // 默认值 20
    private int maxActive;

    /**
     * 配置 Druid 数据源（高性能连接池）
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setInitialSize(initialSize);
        ds.setMaxActive(maxActive);
        // 可根据需要添加更多 Druid 配置：验证查询、空闲回收等
        ds.setValidationQuery("SELECT 1");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        return ds;
    }

    /**
     * 事务管理器，基于 DataSource 实现
     * @param dataSource 自动注入的数据源
     * @return DataSourceTransactionManager
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}