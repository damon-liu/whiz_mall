package com.damon.common.core;

/**
 * @description: 响应结果枚举
 * @author: damon.liu
 * @createDate: 2021/3/15
 */
public enum ResponseCodeEnum {

    /* -------------------------------  system  --------------------------------- */

    SUCCESS(0, "success"),

    FAIL(1, "fail"),

    LACK_PARAMS(2, "缺少参数"),

    PARAM_ERROR(3, "参数错误"),




    NO_PERMISSION_TO_DO(100, "您没有权限进行此操作"),

    TOO_BUSY(102, "业务繁忙请稍后再试");



    /* -------------------------------  base  --------------------------------- */



    /* -------------------------------  dms  --------------------------------- */


    /* -------------------------------  crm  --------------------------------- */


    ResponseCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static ResponseCodeEnum getEnum(Integer code) {
        if (code != null) {
            for (ResponseCodeEnum item : values()) {
                if (item.code.intValue() == code.intValue()) {
                    return item;
                }
            }
        }
        return null;
    }

    }
