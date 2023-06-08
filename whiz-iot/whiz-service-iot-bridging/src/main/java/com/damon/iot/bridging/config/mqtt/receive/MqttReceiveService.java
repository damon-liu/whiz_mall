package com.damon.iot.bridging.config.mqtt.receive;

import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import com.damon.iot.bridging.utils.enums.IotServiceType;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 9:17
 */
public interface MqttReceiveService {

    String V5_TOPIC_FORM = "$share/%s/%s";

    /**
     * 配置信息
     *
     * @return
     */
    MqttProperties mqttProperties();


    /**
     * 服务类型
     *
     * @return
     */
    IotServiceType iotServiceType();

    /**
     * mqtt客户端
     *
     * @return
     */
    MqttClient mqttClient();

    /**
     * 订阅所有
     */
    void subscribeAll();

    /**
     * 连接
     *
     * @throws MqttException
     */
    void connect() throws MqttException;

    /**
     * 订阅消息
     *
     * @param topic
     * @param qos
     */
    void subscribe(String topic, int qos);

    /**
     * 消息投送回调
     *
     * @param topic
     * @param mqttMessage
     */
    void messageArrivedCallback(String topic, MqttMessage mqttMessage);
}
