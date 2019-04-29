package com.shapestudio.common.autoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Web项目自动配置
 */
@Configuration
@ConditionalOnWebApplication
public class WebApplicationAutoConfig {

    /**
     * 登录拦截url
     */
    @Value("${shape.except.login.urls:}")
    private String exceptLoginInterceptUrls;
}
