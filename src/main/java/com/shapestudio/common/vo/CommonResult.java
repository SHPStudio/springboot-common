package com.shapestudio.common.vo;

import lombok.Data;

/**
 * 通用返回结果集
 */
@Data
public class CommonResult<T> {
    /**
     * 默认错误信息
     */
    private final static String DEFAULT_FAIL_MESSAGE = "服务器繁忙,请稍后重试！";

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String message;

    private T data;

    /**
     * 构建错误结果集
     * @param message 错误信息
     * @return
     */
    public static CommonResult failResult(String message) {
        CommonResult failResult = new CommonResult();
        failResult.setMessage(message);
        return failResult;
    }

    /**
     * 带有默认错误信息的错误结果集
     * @return
     */
    public static CommonResult failResultWithDefaultMessage() {
        CommonResult failResult = new CommonResult();
        failResult.setMessage(DEFAULT_FAIL_MESSAGE);
        return failResult;
    }

    /**
     * 构建成功结果集
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult successResult(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }
}
