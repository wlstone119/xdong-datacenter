package com.xdong.datacenter.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

/**
 * 类DubboConfig.java的实现描述：dubbo配置类
 * 
 * @author wanglei 2018年6月5日 上午10:38:44
 */
@Configuration
public class DubboConfig implements EnvironmentAware {

    private static String  applicationName;
    private static String  registryServer;
    private static String  registryAddress;
    private static String  registryProtocol;
    private static String  registryFile;
    private static String  registryCluster;
    private static Integer protocolPort;
    private static Integer protocolPayload;

    private static String  providerGroup;
    private static Integer providerTimeout;
    private static Integer providerRetries;
    private static Integer providerDelay;
    private static String  providerPackage;

    @Override
    public void setEnvironment(Environment environment) {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "spring.dubbo.");

        applicationName = resolver.getProperty("application.name");

        registryServer = resolver.getProperty("registry.server");
        registryAddress = resolver.getProperty("registry.address");
        registryProtocol = resolver.getProperty("registry.protocol");
        registryFile = System.getProperty("user.home") + resolver.getProperty("registry.file");
        registryCluster = resolver.getProperty("registry.cluster");

        protocolPort = Integer.parseInt(resolver.getProperty("protocol.port"));
        protocolPayload = Integer.parseInt(resolver.getProperty("protocol.payload"));

        providerGroup = resolver.getProperty("provider.group");
        providerTimeout = StringUtils.isNotBlank(resolver.getProperty("provider.timeout")) ? Integer.parseInt(resolver.getProperty("provider.timeout")) : null;
        providerRetries = StringUtils.isNotBlank(resolver.getProperty("provider.retries")) ? Integer.parseInt(resolver.getProperty("provider.retries")) : null;
        providerDelay = StringUtils.isNotBlank(resolver.getProperty("provider.delay")) ? Integer.parseInt(resolver.getProperty("provider.delay")) : null;
        providerPackage = resolver.getProperty("provider.package");
    }

    /**
     * 以下为基础配置
     * 
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        applicationConfig.setOwner(applicationName);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setServer(registryServer);
        registryConfig.setAddress(registryAddress);
        registryConfig.setProtocol(registryProtocol);
        // registryConfig.setFile(registryFile);
        // registryConfig.setCluster(registryCluster);
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(protocolPort);
        // protocolConfig.setPayload(protocolPayload);
        return protocolConfig;
    }

    @Bean
    public MonitorConfig monitorConfig() {
        MonitorConfig mc = new MonitorConfig();
        mc.setProtocol("registry");
        return mc;
    }

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setFilter("providerLoggerFilter");
        providerConfig.setGroup(providerGroup);
        providerConfig.setTimeout(providerTimeout);
        providerConfig.setRetries(providerRetries);
        // providerConfig.setDelay(providerDelay);
        providerConfig.setMonitor(monitorConfig());
        return providerConfig;
    }

    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(providerPackage);
        return annotationBean;
    }

}
