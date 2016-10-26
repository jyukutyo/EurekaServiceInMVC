package com.jyukutyo;

import java.nio.charset.Charset;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.transport.netty.http.LoadBalancingHttpClient;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class NameServiceImpl implements NameService {

    @Autowired
    LoadBalancingHttpClient<ByteBuf, ByteBuf> ribbonClient;

    @HystrixCommand
    @Override
    public String name() {
        HttpClientRequest<ByteBuf> request = HttpClientRequest.createGet("/jyukutyo");

        Observable<HttpClientResponse<ByteBuf>> observable = ribbonClient.submit(request);
        return observable.flatMap(resp -> resp.getContent().map(bb -> bb.toString(Charset.defaultCharset()))).toBlocking().first();

    }
}
