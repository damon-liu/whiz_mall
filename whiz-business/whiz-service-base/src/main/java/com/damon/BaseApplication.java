package com.damon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.base.mapper"})
@EnableFeignClients({"com.damon.common.feign","com.damon.base.mangage"})
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run( BaseApplication.class);
    }
}
