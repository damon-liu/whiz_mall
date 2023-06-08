package com.damon.iot.bridging.config.mqtt.publish;

import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties4Rbt;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 14:36
 */
@Service
@ConditionalOnProperty(prefix = "spring.rbtmqtt", name = "enable", matchIfMissing = true)
public class MqttPublish4Rbt extends MqttPublishAbstract{

    @Autowired
    private MqttProperties4Rbt properties;

    @Autowired(required = false)
    @Qualifier("rbtMqttClient")
    private MqttClient mqttClient;

    @Override
    public MqttClient client() {
        return this.mqttClient;
    }

    @Override
    public MqttProperties properties() {
        return this.properties;
    }


    /**
     * 发布 消息
     *
     * @param qos         连接方式 0 最多一次 1:最少一次 2：仅一次
     * @param retained    是否保留
     * @param topic       主题
     * @param pushMessage 消息体 16进制 hex
     */
    @Override
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        this.publish(qos, retained, topic, pushMessage.getBytes(StandardCharsets.UTF_8));
    }
}
