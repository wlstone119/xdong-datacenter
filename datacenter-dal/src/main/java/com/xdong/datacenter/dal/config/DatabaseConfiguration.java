package com.xdong.datacenter.dal.config;

import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

@Configuration
@MapperScan(value = "com.weidai.creditcenter.dal.mapper.*")
public class DatabaseConfiguration implements EnvironmentAware {

    private static final Logger     LOGGER = org.slf4j.LoggerFactory.getLogger(DatabaseConfiguration.class);

    private Environment             environment;

    private RelaxedPropertyResolver datasourcePropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.datasourcePropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        if (StringUtils.isEmpty(datasourcePropertyResolver.getProperty("url"))) {

            LOGGER.debug("Your database connection pool configuration is incorrect!"
                         + " Please check your Spring profile, current profiles are:"
                         + Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(datasourcePropertyResolver.getProperty("url"));
        druidDataSource.setUsername(datasourcePropertyResolver.getProperty("username"));
        druidDataSource.setPassword(datasourcePropertyResolver.getProperty("password"));
        druidDataSource.setInitialSize(Integer.parseInt(datasourcePropertyResolver.getProperty("initialSize")));
        druidDataSource.setMinIdle(Integer.parseInt(datasourcePropertyResolver.getProperty("minIdle")));
        druidDataSource.setMaxActive(Integer.parseInt(datasourcePropertyResolver.getProperty("maxActive")));
        druidDataSource.setMaxWait(Integer.parseInt(datasourcePropertyResolver.getProperty("maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(datasourcePropertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(datasourcePropertyResolver.getProperty("minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(datasourcePropertyResolver.getProperty("validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(datasourcePropertyResolver.getProperty("poolPreparedStatements")));
        druidDataSource.setMaxOpenPreparedStatements(Integer.parseInt(datasourcePropertyResolver.getProperty("maxOpenPreparedStatements")));
        return druidDataSource;
    }

    /**
     * 【mybatisPlus】mp封装的翻页插件，不加时会存在隐患
     * 
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
