package com.damon.iot.bridging.config.mqtt.publish;

import cn.hutool.core.util.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 14:27
 */
@Slf4j
public abstract class MqttPublishAbstract implements MqttPublishService {

    /**
     * 发布
     *
     * @param topic       主题
     * @param pushMessage 消息体
     */
    @Override
    public void publishByDefaultConfig(String topic, String pushMessage) {
        this.publish(this.properties().getQos(), this.properties().getRetained(), topic, pushMessage);
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
        this.publish(qos, retained, topic, HexUtil.decodeHex(pushMessage));
    }

    /**
     * 发送消息
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, byte[] pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage);
        MqttTopic mTopic = this.client().getTopic(topic);
        if (null == mTopic) {
            log.error("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
