package com.shapestudio.common.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.shapestudio.common.util.ApplicationContextUtil;
import com.shapestudio.common.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.*;

/**
 * 通用异常处理
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionResolver extends DefaultErrorAttributes {

    /**
     * 要输出日志的请求头信息
     */
    private final Set<String> printHeader = Sets.newHashSet("referer", "user-agent", "accept");


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 必须要初始化并返回 要不response没有效果
        ModelAndView modelAndView = new ModelAndView();

        // 获取controller具体的方法的类
        HandlerMethod method = (HandlerMethod)handler;
        Class returnClass = method.getMethod().getReturnType();

        // 构建返回的response
        String result = "null";
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        /*  使用response返回    */
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setCharacterEncoding("utf-8");

        // 跨域处理
        String flag = ApplicationContextUtil.getPropertyByKey("shape.cors.enable");
        if ("true".equals(flag)) {
            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        // 如果是post请求传来json参数的话 解析成字符串
        String requestBody = null;
        if (isRequestBody(request)) {
            requestBody = getJSONParam(request);
        }

        // 打印入参、异常和请求头等信息
        printRequest(request, requestBody, ex, method);

        // 是否是参数异常
        if (ex instanceof ValidationException) {
            result = JSON.toJSONString(CommonResult.failResult(ex.getMessage()));
        }else {
            // 如果是集合类就返回空数组，如果是通用结果集就默认返回错误信息
            if (Collection.class.isAssignableFrom(returnClass)) {
                result = "[]";
            }else if (CommonResult.class.equals(returnClass)) {
                //返回通用结果集
                result = JSON.toJSONString(CommonResult.failResultWithDefaultMessage());
            }
        }

        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            // ignore
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds() + "秒");
        return modelAndView;
    }

    /**
     * 判断传来的值是否是json
     * @param request
     * @return
     */
    private boolean isRequestBody(HttpServletRequest request) {
        String contentTypeHeader = request.getHeader("Content-Type");
        if (contentTypeHeader != null && contentTypeHeader.contains("application/json")) {
            return true;
        }
        return false;
    }

    /**
     * 打印错误日志
     * @param request
     * @param requestBody
     */
    private void printRequest(HttpServletRequest request, String requestBody, Exception ex, HandlerMethod handlerMethod) {
        JSONObject printObject = new JSONObject();
        printObject.put("url", request.getRequestURL());
        printObject.put("method", handlerMethod.getBeanType().getName() + "#" + handlerMethod.getMethod().getName());
        if (StringUtils.isBlank(requestBody)) {
            if (!CollectionUtils.isEmpty(request.getParameterMap())) {
                printObject.put("paramMap", request.getParameterMap());
            }
        }else {
            printObject.put("requestBody", requestBody);
        }
        Map<String, List<String>> headerMap = Maps.newHashMap();
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String name = requestHeaderNames.nextElement();
            if (printHeader.contains(name.toLowerCase())) {
                headerMap.put(name, Collections.list(request.getHeaders(name)));
            }
        }
        printObject.put("headers", headerMap);
        log.error("GlobalExceptionResolver.printRequest requestInfo:{}", printObject, ex);
    }

    private String getJSONParam(HttpServletRequest request){
        try {
           String content = new String(FileCopyUtils.copyToByteArray(request.getInputStream()), "utf-8");
           return content;
            // 直接将json信息打印出来
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
