package com.damon.comon.component.version;

import java.lang.annotation.*;

/**
 * Description: 自定义@ApiVersion注解
 *
 * @author damon.liu
 * Date 2023-04-30 2:40
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 标识版本号，从1开始
     */
    int value() default 1;

}