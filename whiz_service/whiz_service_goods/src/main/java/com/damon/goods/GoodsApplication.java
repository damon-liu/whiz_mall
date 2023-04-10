package com.damon.goods;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.damon.goods.dao"})
public class GoodsApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}