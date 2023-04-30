package com.damon.common.redis.resubmit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:切面注解 防重复提交
 *
 * @author damon.liu
 * Date 2023-04-30 8:39
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReSubmitAnno {

    /**
     * 重复提交时间
     * @return
     */
    int times() default 2;

    /**
     * 防重复key是否按参数拼接 ,默认 用token + uri
     * @return
     */
    boolean keyByParam() default true;
}
