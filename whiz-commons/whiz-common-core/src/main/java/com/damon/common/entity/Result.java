package com.damon.common.entity;


import com.damon.common.core.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: damon
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

    public static <T> Result<T> succeed(String msg) {
        return of(null, ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return of(model, ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model) {
        return of(model, ResponseCodeEnum.SUCCESS.getCode(), "success");
    }

    public static <T> Result<T> of(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return of(null, ResponseCodeEnum.FAIL.getCode(), msg);
    }

    public static <T> Result<T> failed() {
        return of(null, ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getDesc());
    }


    public static <T> Result<T> failed(Integer code, String msg) {
        return of(null, code, msg);
    }

    public static <T> Result<T> failed(Integer code, T model, String msg) {
        return of(null, code, msg);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return of(model, ResponseCodeEnum.FAIL.getCode(), msg);
    }
}
