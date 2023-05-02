package com.damon.common.exception;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.damon.common.core.ResponseCodeEnum;
import com.damon.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

/**
 * @description: 异常通用处理
 * @author: damon.liu
 * @createDate: 2021/2/19
 */
@Slf4j
@ResponseBody
public class DefaultExceptionAdvice {

    /**
     * IllegalArgumentException异常处理返回json
     * 返回状态码:400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public Result badRequestException(IllegalArgumentException e) {
        return defHandler("参数解析失败", e);
    }

    /**
     * AccessDeniedException异常处理返回json
     * 返回状态码:403
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public Result badMethodExpressException(AccessDeniedException e) {
        return codeMsgHandler(HttpStatus.FORBIDDEN.value(), "您没有权限执行该方法！", e);
    }

    /**
     * 请求方法异常
     * 返回状态码:405
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return defHandler("请求方法错误", e);
    }

    /**
     * IdempotencyException 幂等性异常
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IdempotencyException.class)
    public Result handleException(IdempotencyException e) {
        return Result.failed(e.getMessage());
    }

    /**
     * 返回状态码:415
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return defHandler("不支持当前媒体类型", e);
    }

    /**
     * SQLException sql异常处理
     * 返回状态码:500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class})
    public Result handleSqlException(SQLException e) {
        return codeMsgHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL执行异常！", e);
    }

    /**
     * 参数缺失异常
     *
     * @param e 异常
     * @return 返回值
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        return enumHandler(ResponseCodeEnum.LACK_PARAMS, e);
    }

    /**
     * 参数错误异常
     *
     * @param e 异常
     * @return 返回值
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return enumHandler(ResponseCodeEnum.PARAM_ERROR, e);
    }

    /**
     * 参数验证异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {
        return enumHandler(ResponseCodeEnum.PARAM_ERROR, e);
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public Result handleException(BusinessException e) {
        String msg = e.getMessage();
        if (StringUtils.isBlank(msg)) {
            msg = "业务执行异常！";
        }
        return codeMsgHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, e);
    }

    /**
     * 所有异常统一处理
     * 返回状态码:500
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return defHandler("系统异常", e);
    }


    private Result defHandler(String msg, Exception e) {
        log.error(msg, e);
        return Result.failed(msg);
    }

    private Result codeMsgHandler(Integer code, String msg, Exception e) {
        log.error(msg, e);
        return Result.failed(code, msg);
    }

    private Result enumHandler(ResponseCodeEnum responseCodeEnum, Exception e) {
        String msg = responseCodeEnum.getDesc();
        log.error(msg, e);
        return Result.failed(responseCodeEnum.getCode(), msg);
    }
}
