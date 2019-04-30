package com.shapestudio.common.autoconfig;

import com.google.common.collect.Lists;
import com.shapestudio.common.interceptor.CorsInterceptor;
import com.shapestudio.common.interceptor.DefaultCorsInterceptor;
import com.shapestudio.common.interceptor.LoginInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Optional;

/**
 * Web项目自动配置
 */
@Configuration
@ConditionalOnWebApplication
public class WebApplicationAutoConfig {
    /**
     * 配置文件url分隔符
     */
    private final static String CONFIG_FILE_URL_SPLIT_MARK = ",";

    /**
     * web mvc相关配置
     */
    @Configuration
    public static class WebMvcAutoConfig implements WebMvcConfigurer, ApplicationContextAware {
        private ApplicationContext applicationContext;

        /**
         * 是否开启Cors
         */
        @Value("${shape.cors.enable:false}")
        private boolean enableCors;

        /**
         * 登录拦截排除的url
         */
        @Value("${shape.except.login.urls:}")
        private String exceptLoginInterceptUrls;

        /**
         * Cors需要排除的url
         */
        @Value("${shape.except.cors.urls:}")
        private String exceptCorsUrls;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 1.检查loginInterceptor
            LoginInterceptor loginInterceptor = getBeanFromApplicationContext(LoginInterceptor.class);
            if (null != loginInterceptor) {
                registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(Optional.ofNullable(exceptLoginInterceptUrls)
                    .filter(StringUtils::isNoneBlank).map(str -> Lists.newArrayList(str.split(CONFIG_FILE_URL_SPLIT_MARK))).orElse(Lists.newArrayList()));
            }

            // 2.是否开启了cors
            if (enableCors) {
                // 3.检查corsInterceptor
                CorsInterceptor corsInterceptor = getBeanFromApplicationContext(CorsInterceptor.class);
                if (null == corsInterceptor) {
                    corsInterceptor = new DefaultCorsInterceptor();
                }
                registry.addInterceptor(corsInterceptor).addPathPatterns("/**").excludePathPatterns(Optional.ofNullable(exceptCorsUrls)
                        .filter(StringUtils::isNoneBlank).map(str -> Lists.newArrayList(str.split(CONFIG_FILE_URL_SPLIT_MARK))).orElse(Lists.newArrayList()));
            }
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        /**
         * 从ApplicationContext中根据特定类返回bean
         * @param targetClass
         * @param <T>
         * @return
         */
        private <T> T getBeanFromApplicationContext(Class<T> targetClass) {
            try {
                return applicationContext.getBean(targetClass);
            }catch (Exception e) {
                // ignore
                return null;
            }

        }
    }
}
