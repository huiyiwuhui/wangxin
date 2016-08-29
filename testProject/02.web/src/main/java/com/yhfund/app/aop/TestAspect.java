package com.yhfund.app.aop;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class TestAspect {
//
//    public void doAfter(JoinPoint jp) {
//        System.out.println("log Ending method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
//    }

    public Object doAround(ProceedingJoinPoint invocation) throws Throwable {
        // 创建一个计时器
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start();
        // 执行方法
        Object object = invocation.proceed();
        // 计时器停止
        watch.stop();

        String packagename = invocation.getSignature().getDeclaringTypeName();
        // 方法名称
        String methodName = invocation.getSignature().getName();

        // 获取计时器计时时间
        Long time = watch.getTime();
        System.out.println("Times:【"+(Double.parseDouble(time+"")/1000)+"】秒， Method:【"+methodName+"】, package:"+packagename);
        System.out.println();
        return object;
    }

//    public void doBefore(JoinPoint jp) {
//        System.out.println("log Begining method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
//    }
//
//    public void doThrowing(JoinPoint jp, Throwable ex) {
//        System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
//        System.out.println(ex.getMessage());
//    }
}