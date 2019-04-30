package com.shapestudio.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class PerformanceAnnotationHandler implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        if (log.isWarnEnabled()) {
            log.warn("PerformanceAnnotationHandler.invoke end thread:{} invoke:{}.{} ellps:{} mills", Thread.currentThread().getName(), invocation.getThis().getClass().getCanonicalName(),
                    invocation.getMethod().getName(),
                    (System.currentTimeMillis() - start));
        }
        return result;
    }
}
