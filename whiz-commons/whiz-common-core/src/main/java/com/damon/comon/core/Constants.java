package com.damon.comon.core;

/**
 * 常量
 *
 */
public interface Constants {
    //购物车
    String REDIS_CART = "cart_";
    //待支付订单key
    String REDIS_ORDER_PAY = "order_pay_";
    //秒杀商品key
    String SECKILL_GOODS_KEY="seckill_goods_";
    //秒杀商品库存数key
    String SECKILL_GOODS_STOCK_COUNT_KEY="seckill_goods_stock_count_";
    //秒杀用户key
    String SECKILL_USER_KEY = "seckill_user_";

    String LOCK_KEY_PREFIX = "LOCK_KEY";
}
