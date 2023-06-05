package com.damon.iot.bridging.config.mqtt.receive;

import com.alibaba.fastjson.JSONObject;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties4Rbt;
import com.damon.iot.bridging.utils.Md5Utils;
import com.damon.iot.bridging.utils.enums.IotServiceType;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 14:09
 */
@Getter
@Slf4j
@Component
@ConditionalOnProperty(prefix = "spring.rbtmqtt", name = "enable", matchIfMissing = true)
public class MqttReceive4Rbt extends MqttClientReceiveAbstract {

    @Autowired
    private MqttProperties4Rbt properties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public MqttProperties mqttProperties() {
        return this.properties;
    }

    @Override
    public IotServiceType iotServiceType() {
        return IotServiceType.RBT;
    }

    @SneakyThrows
    @Override
    @Bean("rbtMqttClient")
    public MqttClient mqttClient() {
        String clientId = properties.getClientId();
        super.client = new MqttClient(this.mqttProperties().getHostUrl(), clientId, new MemoryPersistence());
        return super.client;
    }

    @SneakyThrows
    @Override
    public void messageArrivedCallback(String topic, MqttMessage mqttMessage) {
        // subscribe后得到的消息会执行到这里面
        String mess = new String(mqttMessage.getPayload(), "utf-8");
        String key = Md5Utils.MD5Encode(topic + mess, "utf-8");
        boolean flag = this.redisTemplate.opsForValue().setIfAbsent("LBT-mqtt-mess:" + key, 1, 10, TimeUnit.SECONDS);
        if (flag) {
            Object parse = JSONObject.parse(mess);
            System.out.println("rbt接收消息:  " + parse);
        } else {
            log.info("mqtt 重复数据过滤");
        }
    }


}
