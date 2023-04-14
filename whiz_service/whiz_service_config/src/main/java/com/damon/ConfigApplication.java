package com.damon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.config.dao"})
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run( ConfigApplication.class);
    }
}
