package com.yhfund.app.aop;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * MethodTimeActive
 * Created by zhengdw
 * 2016/6/16.
 * 10:18
 */
public class MethodTimeActive {

    private Logger logger = Logger.getLogger(getClass());

    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {

        // 创建一个计时器
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start();
        // 执行方法
        Object object = invocation.proceed();
        // 计时器停止
        watch.stop();

        String packageName = invocation.getSignature().getDeclaringTypeName();
        // 方法名称
        String methodName = invocation.getSignature().getName();

        // 获取计时器计时时间
        Long time = watch.getTime();
        logger.info("Times:【"+(Double.parseDouble(time+"")/1000)+"】秒， Method:【"+methodName+"】, package:"+packageName);
        System.out.println("Times:【"+(Double.parseDouble(time+"")/1000)+"】秒， Method:【"+methodName+"】, package:"+packageName);
        return object;
    }
}
