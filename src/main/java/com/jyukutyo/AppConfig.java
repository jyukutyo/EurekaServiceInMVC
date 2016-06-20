package com.jyukutyo;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = "com.jyukutyo")
@EnableWebMvc
@Configuration
public class AppConfig {

    @Bean
    public ApplicationInfoManager applicationInfoManager() {
        EurekaInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        return new ApplicationInfoManager(instanceConfig, instanceInfo);
    }

    @Bean
    public EurekaClient eurekaClient(ApplicationInfoManager applicationInfoManager) {
        return new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig());
    }

    @Bean
    public DynamicPropertyFactory dynamicPropertyFactory() {
        return com.netflix.config.DynamicPropertyFactory.getInstance();
    }

}