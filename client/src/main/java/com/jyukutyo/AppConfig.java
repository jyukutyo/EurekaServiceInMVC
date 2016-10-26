package com.jyukutyo;

import java.io.IOException;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import io.netty.buffer.ByteBuf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAspectJAutoProxy
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
        return DynamicPropertyFactory.getInstance();
    }

    @Bean
    public LoadBalancingHttpClient<ByteBuf, ByteBuf> loadBalancingHttpClient() throws IOException {
        ConfigurationManager.loadPropertiesFromResources("eureka.properties");

        IClientConfig clientConfig = IClientConfig.Builder.newBuilder("sample-client").build();
        LoadBalancingHttpClient<ByteBuf, ByteBuf> ribbonClient = RibbonTransport.newHttpClient(clientConfig);
        return ribbonClient;
    }

    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

}
