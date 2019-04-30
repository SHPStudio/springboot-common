package com.shapestudio.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>登录自定义拦截器接口</p>
 * <p>如果需要自定义登录拦截的逻辑可以直接实现该接口并重写{@link HandlerInterceptor#preHandle}</p>
 * <p>不过需要将实现类放入spring容器中才可生效</p>
 */
public interface LoginInterceptor extends HandlerInterceptor {
}
