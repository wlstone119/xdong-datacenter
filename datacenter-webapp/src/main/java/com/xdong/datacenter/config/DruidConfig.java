package com.xdong.datacenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 查看地址http://ip:port/druid/index.html
 */
@Configuration
public class DruidConfig {

    @Value("${druid.logSlowSql:true}")
    private String logSlowSql;

    @Value("${druid.whiteIps:192.168.0.0/16,172.20.1.0/24,172.20.100.0/24,127.0.0.1}")
    private String whiteIps;

    /**
     * 连接池请求
     * 
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("logSlowSql", logSlowSql);
        reg.addInitParameter("allow", whiteIps);
        reg.addInitParameter("resetEnable", "false");
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

}
