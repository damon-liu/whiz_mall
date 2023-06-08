package com.damon.iot.bridging.config.mqtt.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 9:24
 */
@Setter
@Getter
public class MqttProperties {

    /**
     * 连接方式 0 最多一次 1:最少一次 2：仅一次
     */
    private Integer qos = 1;

    private Integer maxReConnectNumbers = 100;

    /**
     * 是否开启共享订阅
     */
    private Boolean enableShare = false;

    /**
     * 下发消息默认 不保利
     */
    private Boolean retained = false;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String modemSecret;

    /**
     * 连接地址
     */
    private String hostUrl;

    private String password;

    private String clientId;

    /**
     * 默认连接话题
     */
    private String defaultTopic;

    private List<String> subscribeTopics;

    /**
     * 超时时间
     */
    private int timeout;
    /**
     * 保持连接数
     */
    private int keepalive;
}
