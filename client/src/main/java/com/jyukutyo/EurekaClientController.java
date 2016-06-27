package com.jyukutyo;

import java.nio.charset.Charset;

import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.loadbalancer.DefaultNIWSServerListFilter;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.EurekaNotificationServerListUpdater;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import com.netflix.ribbon.transport.netty.RibbonTransport;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rx.Observable;

@Controller
public class EurekaClientController {

    @Autowired
    EurekaClient eurekaClient;

    @RequestMapping("/example")
    @ResponseBody
    public String example() throws Exception {
        ConfigurationManager.loadPropertiesFromResources("eureka.properties");

        IClientConfig clientConfig = IClientConfig.Builder.newBuilder("sample-client").build();
        DiscoveryEnabledNIWSServerList serverList = new DiscoveryEnabledNIWSServerList(clientConfig, () -> eurekaClient);
        ILoadBalancer loadBalancer = new ZoneAwareLoadBalancer(clientConfig, new RandomRule(), new NIWSDiscoveryPing(), serverList, new DefaultNIWSServerListFilter(), new EurekaNotificationServerListUpdater());

        LoadBalancingHttpClient<ByteBuf, ByteBuf> ribbonClient = RibbonTransport.newHttpClient(loadBalancer);
        HttpClientRequest<ByteBuf> request = HttpClientRequest.createGet("/jyukutyo");

        Observable<HttpClientResponse<ByteBuf>> observable = ribbonClient.submit(request);
        return observable.flatMap(resp -> resp.getContent().map(bb -> bb.toString(Charset.defaultCharset()))).toBlocking().first();
    }

}