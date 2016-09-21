package com.wangxin.app.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring加载工厂
 *
 * @author jw.fang
 * @version 1.0
 */
public class SpringBeanForDubboFactory {
    private static SpringBeanForDubboFactory instance;
    private BeanFactory beanFactory;

    private SpringBeanForDubboFactory() {
        load();
    }

    public void load() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        beanFactory = ctx;
    }

    public static SpringBeanForDubboFactory init() {
        if (null == instance) {
            instance = new SpringBeanForDubboFactory();
        }
        return instance;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
