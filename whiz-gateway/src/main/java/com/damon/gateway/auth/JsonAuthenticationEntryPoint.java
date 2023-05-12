package com.damon.gateway.auth;

import com.damon.common.utils.WebfluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description:401未授权异常处理，转换为JSON
 *
 * @author damon.liu
 * Date 2023-05-11 7:56
 */
@Slf4j
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return WebfluxResponseUtil.responseFailed(exchange, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}