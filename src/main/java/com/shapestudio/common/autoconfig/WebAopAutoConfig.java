package com.shapestudio.common.autoconfig;

import com.shapestudio.common.annotations.LogException;
import com.shapestudio.common.annotations.Performance;
import com.shapestudio.common.handler.LogExceptionAnnotationHandler;
import com.shapestudio.common.handler.PerformanceAnnotationHandler;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebAopAutoConfig {

    @Configuration
    @ConditionalOnProperty(value = "shape.aop.logexception.enable", havingValue = "true", matchIfMissing = true)
    public static class LogExceptionAopConfig{
        @Bean
        public LogExceptionAnnotationHandler logExceptionAnnotationHandler() {
            return new LogExceptionAnnotationHandler();
        }

        @Bean
        public DefaultPointcutAdvisor logExceptionAdvisor() {
            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(LogException.class, true);
            advisor.setPointcut(pointcut);
            advisor.setAdvice(logExceptionAnnotationHandler());
            return advisor;
        }

        @Bean
        public DefaultPointcutAdvisor logExceptionMethodAdvisor() {
            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, LogException.class);
            advisor.setPointcut(pointcut);
            advisor.setAdvice(logExceptionAnnotationHandler());
            return advisor;
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "shape.aop.performance.enable", havingValue = "true", matchIfMissing = true)
    public static class PerformaceAopConfig{
        @Bean
        public PerformanceAnnotationHandler performanceAnnotationHandler() {
            return new PerformanceAnnotationHandler();
        }

        @Bean
        public DefaultPointcutAdvisor performaceAdvisor() {
            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(Performance.class, true);
            advisor.setPointcut(pointcut);
            advisor.setAdvice(performanceAnnotationHandler());
            return advisor;
        }

        @Bean
        public DefaultPointcutAdvisor performaceMethodAdvisor() {
            DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, Performance.class);
            advisor.setPointcut(pointcut);
            advisor.setAdvice(performanceAnnotationHandler());
            return advisor;
        }
    }
}
