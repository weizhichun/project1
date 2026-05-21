package com.wzc.common.utils;

import com.wzc.common.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultUtil<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ResultUtil<T> success() {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(CommonConstant.SUCCESS_MSG);
        return result;
    }

    public static <T> ResultUtil<T> success(T data) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(CommonConstant.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static <T> ResultUtil<T> success(String msg, T data) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ResultUtil<T> fail() {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.FAIL_CODE);
        result.setMsg(CommonConstant.FAIL_MSG);
        return result;
    }

    public static <T> ResultUtil<T> fail(String msg) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.FAIL_CODE);
        result.setMsg(msg);
        return result;
    }

    public static <T> ResultUtil<T> fail(Integer code, String msg) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}