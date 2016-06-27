package com.jyukutyo;

import java.nio.charset.Charset;

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
    LoadBalancingHttpClient<ByteBuf, ByteBuf> ribbonClient;

    @RequestMapping("/")
    @ResponseBody
    public String example() throws Exception {
        HttpClientRequest<ByteBuf> request = HttpClientRequest.createGet("/jyukutyo");

        Observable<HttpClientResponse<ByteBuf>> observable = ribbonClient.submit(request);
        return observable.flatMap(resp -> resp.getContent().map(bb -> bb.toString(Charset.defaultCharset()))).toBlocking().first();
    }

}