package com.damon.gateway.filter;

import com.damon.common.constant.CommonConstant;
import com.damon.common.constant.ConfigConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-17 8:23
 */

@Component
public class IpLbIsolationFilter implements GlobalFilter, Ordered {
    @Value("${" + ConfigConstants.CONFIG_LOADBALANCE_ISOLATION_ENABLE + ":}")
    private Boolean enableVersionControl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (Boolean.TRUE.equals(enableVersionControl)
                && exchange.getRequest().getHeaders().containsKey(CommonConstant.LOCK_REMOTE_IP)) {
            String lockIp = exchange.getRequest().getHeaders().get(CommonConstant.LOCK_REMOTE_IP).get(0);
            ServerHttpRequest rebuildRequest = exchange.getRequest().mutate().headers(header -> {
                        header.add(CommonConstant.LOCK_REMOTE_IP, lockIp);
                    }).build();
            ServerWebExchange rebuildServerWebExchange = exchange.mutate().request(rebuildRequest).build();
            return chain.filter(rebuildServerWebExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
