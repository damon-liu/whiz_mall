package com.damon.order.service;


import com.damon.order.entity.Order;
import com.damon.order.feign.AccountFeignClient;
import com.damon.order.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 */
@Slf4j
@Service
public class OrderService {
    @Resource
    private AccountFeignClient accountFeignClient;

    @Resource
    private OrderMapper orderMapper;

    public void create(String userId, String commodityCode, Integer count) {
        //订单金额
        Integer orderMoney = count * 1;

        Order order = new Order()
                .setUserId(userId)
                .setCommodityCode(commodityCode)
                .setCount(count)
                .setMoney(orderMoney);
        orderMapper.insert(order);

        accountFeignClient.reduce(userId, orderMoney);
    }
}
