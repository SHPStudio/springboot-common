package com.shapestudio.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
/**
 * <p>Cors自定义拦截器接口</p>
 * <p>如果需要自定义跨域拦截的逻辑可以直接实现该接口并重写{@link HandlerInterceptor#preHandle}</p>
 * <p>不过需要将实现类放入spring容器中才可生效</p>
 * <p>如果没有自定义的实现但开启了Cors的话，就会使用默认cors拦截逻辑</p>
 * @see DefaultCorsInterceptor
 */
public interface CorsInterceptor extends HandlerInterceptor {
}
