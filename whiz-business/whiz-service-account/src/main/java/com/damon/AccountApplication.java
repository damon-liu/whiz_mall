package com.damon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 *
 * @author damon.liu
 * Date ${DATE} ${TIME}
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.account.mapper"})
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run( AccountApplication.class);
    }
}