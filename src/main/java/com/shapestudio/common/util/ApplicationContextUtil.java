package com.shapestudio.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Application工具类
 */
public class ApplicationContextUtil implements ApplicationContextAware  {

    private static ApplicationContext staticApplicationContext;

    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return staticApplicationContext;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        staticApplicationContext = applicationContext;
    }

    /**
     * 获取特定类型的属性
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getPropertyByResolveType(String key, Class<T> tClass) {
        return staticApplicationContext.getEnvironment().getProperty(key, tClass);
    }

    /**
     * 获取字符串形式的属性
     * @param key
     * @return
     */
    public static String getPropertyByKey(String key) {
        return staticApplicationContext.getEnvironment().getProperty(key);
    }
}