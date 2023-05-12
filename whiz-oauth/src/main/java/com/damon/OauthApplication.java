package com.damon;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = {"com.damon.oauth.mapper"})
@EnableFeignClients
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class);
    }
}