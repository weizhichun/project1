package com.weizhichun.common.utils;

import com.weizhichun.common.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果工具类
 */
@Data
public class ResultUtil<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    // 成功（无数据）
    public static <T> ResultUtil<T> success() {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(CommonConstant.SUCCESS_MSG);
        return result;
    }

    // 成功（有数据）
    public static <T> ResultUtil<T> success(T data) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(CommonConstant.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    // 失败
    public static <T> ResultUtil<T> fail() {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.FAIL_CODE);
        result.setMsg(CommonConstant.FAIL_MSG);
        return result;
    }

    // 自定义失败信息
    public static <T> ResultUtil<T> fail(String msg) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(CommonConstant.FAIL_CODE);
        result.setMsg(msg);
        return result;
    }
}