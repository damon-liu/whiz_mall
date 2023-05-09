package com.damon.common.lb.annotation;

import com.damon.common.lb.config.FeignHttpInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 开启feign拦截器传递数据给下游服务，包含基础数据和http的相关数据
 *
 * @author damon.liu
 * Date 2023-05-09 4:11
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignHttpInterceptorConfig.class})
public @interface EnableFeignInterceptor {
}
