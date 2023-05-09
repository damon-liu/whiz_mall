package com.damon;

import com.damon.common.lb.annotation.EnableFeignInterceptor;
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
@EnableFeignClients(basePackages = {"com.damon.business.feign"})
@EnableFeignInterceptor
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class);
    }
}