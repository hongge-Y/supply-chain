package cn.dsscm.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis 配置类（在 Spring 根容器中加载）
 * 职责：
 * 1. 创建 SqlSessionFactory，整合数据源、映射文件和全局配置
 * 2. 扫描 Mapper 接口并注册为 Spring Bean
 */
@Configuration
public class MyBatisConfig {
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("cn.dsscm.pojo");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:cn/dsscm/dao/*.xml"));
        factory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        return factory;
    }

    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setBasePackage("cn.dsscm.dao");
        return scanner;
    }
}