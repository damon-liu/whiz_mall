package com.damon;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Description:
 *
 * @author damon.liu
 * Date ${DATE} ${TIME}
 */
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.oauth.dao"})
public class AuthApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}