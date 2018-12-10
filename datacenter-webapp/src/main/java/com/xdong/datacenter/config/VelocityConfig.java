package com.xdong.datacenter.config;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.velocity.exception.VelocityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@SuppressWarnings("deprecation")
@Configuration
public class VelocityConfig {

    @Bean
    public VelocityConfigurer velocityConfigurer() throws VelocityException, IOException {
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", "UTF-8");
        velocityProperties.setProperty("output.encoding", "UTF-8");
        velocityConfigurer.setVelocityProperties(velocityProperties);
        velocityConfigurer.setResourceLoaderPath("/templates/");
        velocityConfigurer.afterPropertiesSet();
        return velocityConfigurer;
    }

    @Bean
    public VelocityViewResolver viewResolver(ServletContext servletContext) throws Exception {

        VelocityViewResolver resolver = new VelocityViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".vm");
        resolver.setContentType(MediaType.TEXT_HTML_VALUE + ";charset=utf-8");
        resolver.setExposeSpringMacroHelpers(true);
        resolver.setExposeRequestAttributes(true);
        resolver.setRequestContextAttribute("rc");
        resolver.setDateToolAttribute("dateTool");
        resolver.setNumberToolAttribute("numberTool");
        // resolver.setToolboxConfigLocation("/toolbox.xml");

        return resolver;
    }
}
