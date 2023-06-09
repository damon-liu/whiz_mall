package com.damon.iot.bridging.config.mqtt.receive;

import com.alibaba.fastjson.JSONObject;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties4Icer;
import com.damon.iot.bridging.utils.RabbitSendMsgUtil;
import com.damon.iot.bridging.utils.enums.IotServiceType;
import iot.common.constant.MqttConstants;
import iot.common.entity.MqMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 14:09
 */
@Getter
@Slf4j
@Component
@ConditionalOnProperty(prefix = "spring.mqtt", name = "enable", matchIfMissing = true)
public class MqttReceive4Icr extends MqttClientReceiveAbstract {

    @Autowired
    private MqttProperties4Icer properties;
    @Autowired
    private RabbitSendMsgUtil rabbitMqUtils;

    public static final String PRE_SYS = "$SYS/";

    public static final String TEST_TOPIC = "/test";


    @Override
    public MqttProperties mqttProperties() {
        return this.properties;
    }

    @Override
    public IotServiceType iotServiceType() {
        return IotServiceType.ICER;
    }

    @SneakyThrows
    @Override
    @Bean("icerMqttClient")
    public MqttClient mqttClient() {
        String clientId = properties.getClientId();
        this.client = new MqttClient(this.mqttProperties().getHostUrl(), clientId, new MemoryPersistence());
        return this.client;
    }

    @SneakyThrows
    @Override
    public void messageArrivedCallback(String topic, MqttMessage mqttMessage) {
        // 推送到队列
        try {
            // subscribe后得到的消息会执行到这里面
            MqMessage message = MqMessage.builder()
                    .topic(topic)
                    .serverRecvTime(System.currentTimeMillis())
                    .payload(mqttMessage.getPayload())
                    .build();
            log.info("【icer mqtt】 接收消息 topic：{}", topic);
            if (topic.contains(PRE_SYS) || topic.contains(TEST_TOPIC)) {
                Object parse = JSONObject.parse(mqttMessage.getPayload());
                log.info("消息体：{}", parse);
            }
            this.rabbitMqUtils.asyncSend(this.env + MqttConstants.ICER_TOPIC_EXCHANGE, this.env + MqttConstants.ICER_TOPIC_BINDING_KEY, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
