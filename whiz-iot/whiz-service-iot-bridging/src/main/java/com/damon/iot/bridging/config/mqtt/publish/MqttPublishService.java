package com.damon.iot.bridging.config.mqtt.publish;

import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 14:23
 */
public interface MqttPublishService {

    /**
     * 使用系统配置下发消息
     *
     * @param topic
     * @param pushMessage
     */
    void publishByDefaultConfig(String topic, String pushMessage);

    /**
     * 发送消息
     *
     * @param qos         连接方式 0 最多一次 1:最少一次 2：仅一次
     * @param retained    是否保留消息
     * @param topic
     * @param pushMessage
     */
    void publish(int qos, boolean retained, String topic, String pushMessage);

    /**
     * mqtt 链接
     *
     * @return
     */
    MqttClient client();

    /**
     * 配置信息
     *
     * @return
     */
    MqttProperties properties();

}
