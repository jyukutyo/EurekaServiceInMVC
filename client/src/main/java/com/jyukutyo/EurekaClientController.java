package com.jyukutyo;

import java.net.URI;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EurekaClientController {

    @RequestMapping("/")
    @ResponseBody
    public String example() throws Exception {
        ConfigurationManager.loadPropertiesFromResources("eureka.properties");  // 1
        RestClient client = (RestClient) ClientFactory.getNamedClient("sample-client");  // 2

        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/jyukutyo")).build(); // 3
        for (int i = 0; i < 20; i++)  {
            HttpResponse response = client.executeWithLoadBalancer(request); // 4
            System.out.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus() + " : " + IOUtils.toString(response.getInputStream(), "UTF-8"));
        }
        @SuppressWarnings("rawtypes")
        ZoneAwareLoadBalancer lb2 = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.out.println(lb2.getLoadBalancerStats());
        return "example";
    }

}