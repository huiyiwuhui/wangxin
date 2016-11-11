package com.wangxin.app.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * Created by Administra on 2016/10/27.
 */
@Component
public class TestServiceClient {

    @Reference(version="1.0.0")
    private TestService testService;

    public TestService getTestService() {
        return testService;
    }

    public void test1(){
        testService.test1();
    }

}
