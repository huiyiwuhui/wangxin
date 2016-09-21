package com.wangxin.app.test;

import com.wangxin.app.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administra on 2016/9/21.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring-dubbo-consumer.xml" });
        context.start();

        HelloService demoService = (HelloService) context.getBean("helloService"); //
        demoService.sayHello(); // ִ
        demoService.sayHello("wangxin"); // ִ

    }
}
