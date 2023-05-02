package com.damon.common.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-05-02 9:21
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {

    /**
     * 是否查询SysUser对象所有信息，true则通过rpc接口查询
     */
    boolean isFull() default false;

}
