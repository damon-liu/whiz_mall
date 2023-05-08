package com.damon.common.constant;

/**
 * 常量
 *
 */
public interface CommonConstant {
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

    /**
     * 项目版本号(banner使用)
     */
    String PROJECT_VERSION = "1.0-SNAPSHOT";

    String LOCK_KEY_PREFIX = "LOCK_KEY";

    String DEF_USER_PASSWORD = "123456";

    /**
     * 菜单
     */
    Integer MENU = 1;

    /**
     * 权限
     */
    Integer PERMISSION = 2;


    /**
     * 负载均衡策略-ip 信息头
     */
    String CUSTOM_IP = "custom_ip";

    /**
     * 注册中心元数据 版本号
     */
    String METADATA_VERSION = "version";
}
