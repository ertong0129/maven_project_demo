package com.example.demo.business.config.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.transaction.TransactionDefinition.ISOLATION_DEFAULT;

/**
 * mysql连接配置 目前无法使用acm，先临时使用该类
 */
@MapperScan(basePackages = {DataSourceConstants.MAPPER_PACKAGE})
@Configuration
public class DataSourceConfigWithoutEncryp {

    @Value("${demo.jdbc.driver-class-name}")
    private String driverClassName;

    @Value("${demo.jdbc.url}")
    private String url;

    @Value("${demo.jdbc.name}")
    private String name;

    @Value("${demo.jdbc.password}")
    private String password;

    @Value("${demo.jdbc.max_connections}")
    private Integer maxConnections;

    @Value("${env}")
    private String env;

    @Bean(name = "demoDataSource")
    public DataSource mybatisDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxConnections);
        dataSource.setMaxWait(5000);
        dataSource.setInitialSize(3);
        return dataSource;
    }

    @Bean(name = "demoTransactionManager")
    public DataSourceTransactionManager mybatisTransactionManager(@Qualifier("demoDataSource") DataSource mybatisDataSource) {
        return new DataSourceTransactionManager(mybatisDataSource);
    }

    @Bean(name = "demoSqlSessionFactory")
    public SqlSessionFactory mybatisSqlSessionFactory(@Qualifier("demoDataSource") DataSource mybatisDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mybatisDataSource);
        String locationPattern = DataSourceConstants.MAPPER_LOCATION;
        Resource[] localMapperResources = new PathMatchingResourcePatternResolver()
                .getResources(locationPattern);

        List<Resource> resourceList = new ArrayList<>(localMapperResources.length);
        resourceList.addAll(Arrays.asList(localMapperResources));

        Resource[] resources = new Resource[resourceList.size()];
        sessionFactory.setMapperLocations(resourceList.toArray(resources));

        //拦截器  打印出具体的sql  方便调试排查
        if (!env.equals("online")) {
            sessionFactory.setPlugins(new Interceptor[]{new PrintSqlInterceptor()});
        }

        //驼峰转换
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }

    @Bean(name = "demoTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("demoTransactionManager") DataSourceTransactionManager mybatisTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setIsolationLevel(ISOLATION_DEFAULT);
        transactionTemplate.setTimeout(10);
        transactionTemplate.setTransactionManager(mybatisTransactionManager);
        return transactionTemplate;
    }


}
