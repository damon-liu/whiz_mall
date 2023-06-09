package com.damon.iot.bridging.config.mqtt.receive;

import com.alibaba.fastjson.JSONObject;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties4Rbt;
import com.damon.iot.bridging.utils.RabbitSendMsgUtil;
import com.damon.iot.bridging.utils.UuidUtils;
import com.damon.iot.bridging.utils.enums.IotServiceType;
import iot.common.constant.MqttConstants;
import iot.common.entity.RbtMqMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@ConditionalOnProperty(prefix = "spring.rbtmqtt", name = "enable", matchIfMissing = true)
public class MqttReceive4Rbt extends MqttClientReceiveAbstract {

    @Autowired
    private MqttProperties4Rbt properties;
    @Autowired
    private RabbitSendMsgUtil rabbitMqUtils;
    @Autowired(required = false)
    @Qualifier("rbtRabbitTemplate")
    private RabbitTemplate rbtRabbitTemplate;

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
        RbtMqMessage message = RbtMqMessage.builder()
                .topic(topic)
                .serverRecvTime(System.currentTimeMillis())
                .payload(mqttMessage.getPayload())
                .build();
        log.info("【rbt mqtt】 接收消息 topic：{} , {}", topic, JSONObject.parse(mess));
        String corrId = UuidUtils.getUuid();
        CorrelationData correlationData = new CorrelationData(corrId);
        this.rbtRabbitTemplate.convertAndSend(MqttConstants.RBT_TOPIC_EXCHANGE, "", message, correlationData);
    }


}
