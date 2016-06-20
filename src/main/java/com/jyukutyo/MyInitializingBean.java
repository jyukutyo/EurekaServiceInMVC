package com.jyukutyo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyInitializingBean implements InitializingBean {

    @Autowired
    ExampleServiceBase exampleServiceBase;

    @Override
    public void afterPropertiesSet() throws Exception {
        exampleServiceBase.start();
    }
}
