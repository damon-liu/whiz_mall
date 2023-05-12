package com.damon.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description:
 *
 * @author damon.liu
 * Date ${DATE} ${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }

    // @Bean
    // public KeyResolver ipKeyResolver() {
    //     return new KeyResolver() {
    //         @Override
    //         public Mono<String> resolve(ServerWebExchange exchange) {
    //             return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    //         }
    //     };
    // }

}