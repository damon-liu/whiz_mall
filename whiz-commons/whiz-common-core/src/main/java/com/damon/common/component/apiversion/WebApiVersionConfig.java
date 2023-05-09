package com.damon.common.component.apiversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Description:配置注册HandlerMapping
 *
 * @author damon.liu
 * Date 2023-04-30 2:47
 */
@Slf4j
@Configuration
// @ConditionalOnProperty(prefix = "whiz.apiVersion", name = "enabled", havingValue = "true")
public class WebApiVersionConfig implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiRequestMappingHandlerMapping();
    }
}
