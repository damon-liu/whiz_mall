package com.damon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 *
 * @author damon.liu
 * Date ${DATE} ${TIME}
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.iot.mapper"})
// @EnableFeignClients({"com.damon.common.feign","com.damon.base.mangage"})
public class IotBridgingApplication {
    public static void main(String[] args) {
        SpringApplication.run( IotBridgingApplication.class);
    }
}
