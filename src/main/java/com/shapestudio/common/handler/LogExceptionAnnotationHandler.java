package com.shapestudio.common.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class LogExceptionAnnotationHandler implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("LogExceptionAnnotationHandler Info classAndmethod:{}#{}, param:{}", methodInvocation.getThis().getClass().getCanonicalName(),
                    methodInvocation.getMethod().getName(),
                    JSON.toJSONString(methodInvocation.getArguments()));
        }
        Object result;
        try {
            result = methodInvocation.proceed();
        } catch (Throwable throwable) {
            String methodName =  methodInvocation.getThis().getClass().getCanonicalName() + "#" + methodInvocation.getMethod().getName();
            log.error("LogExceptionAnnotationHandler.invoke except classAndmethod:{}, param:{}", methodName,
                    JSON.toJSONString(methodInvocation.getArguments()), throwable);
            throw throwable;
        }

        if (log.isDebugEnabled()) {
            log.debug("LogExceptionAnnotationHandler Info classAndmethod:{}#{}, result:{}", methodInvocation.getThis().getClass().getCanonicalName(),
                    methodInvocation.getMethod().getName(),
                    JSON.toJSONString(result));
        }

        return result;
    }
}
