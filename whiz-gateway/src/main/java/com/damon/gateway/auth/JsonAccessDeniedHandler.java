package com.damon.gateway.auth;

import com.damon.common.utils.WebfluxResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description:403拒绝访问异常处理，转换为JSON
 *
 * @author damon.liu
 * Date 2023-05-11 8:07
 */
public class JsonAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        return WebfluxResponseUtil.responseFailed(exchange, HttpStatus.FORBIDDEN.value(), e.getMessage());
    }
}
