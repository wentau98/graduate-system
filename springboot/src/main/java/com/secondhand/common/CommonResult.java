package com.secondhand.common;

import lombok.Data;

@Data
public class CommonResult<T> {

    private int code;
    private String msg;
    private T data;

    // Success
    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(200);
        result.setMsg("Operation success");
        result.setData(data);
        return result;
    }

    // Error
    public static <T> CommonResult<T> error(String message) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(500);
        result.setMsg(message);
        result.setData(null);
        return result;
    }

    // Custom status
    public static <T> CommonResult<T> result(int code, String msg, T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    /**
     * 失败返回结果（带提示信息）
     * @param message 错误提示
     */
    public static <T> CommonResult<T> failed(String message) {
        return error(message);
    }

    /**
     * 失败返回结果（默认提示）
     */
    public static <T> CommonResult<T> failed() {
        return failed("操作失败");
    }
}