package com.yhfund.api.test;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeoutException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mybatis.xml"})
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class STServiceTest{

    @Autowired
    private MemcachedClient memcachedClient;


    @Test
    public void test() {

        try {
            memcachedClient.set("xin",10,"wang");
            System.out.println( memcachedClient.get("xin"));

            Thread.sleep(11000);

            System.out.println( memcachedClient.get("xin"));



            memcachedClient.set("hu",10,"chu");
            System.out.println( memcachedClient.get("hu"));

            Thread.sleep(5000);

            System.out.println( memcachedClient.get("hu"));
            Thread.sleep(7000);

            System.out.println( memcachedClient.get("hu"));


            memcachedClient.set("xiaozhi",10,"史伟志");
            System.out.println( memcachedClient.get("xiaozhi"));

            Thread.sleep(5000);

            System.out.println( memcachedClient.get("xiaozhi"));
            Thread.sleep(7000);

            System.out.println( memcachedClient.get("xiaozhi"));


        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test1() {

        try {
            String name = memcachedClient.get("xin");
            System.out.println(name);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }

    }

}
