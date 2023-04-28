package com.damon.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.consume.dao"})
public class SeckillConsumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillConsumeApplication.class,args);
    }
}
