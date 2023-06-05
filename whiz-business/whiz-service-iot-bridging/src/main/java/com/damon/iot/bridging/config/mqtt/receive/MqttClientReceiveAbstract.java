package com.damon.iot.bridging.config.mqtt.receive;

import io.netty.util.internal.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-04 9:38
 */
@Slf4j
public abstract class MqttClientReceiveAbstract implements MqttReceiveService{

    public MqttClient client;

    // @Value("${ice.active}")
    // public String active;

    @Value("${spring.profiles.active}")
    public String env;

    @Value("${iot-service-type}")
    public String serviceType;

    /**
     * 客户端连接
     * select SHA2(CONCAT('clientId=',${clientid},'userName=',${username},mb.modem_secret),256) as password_hash FROM `iot_modem_auth` mb where mb.modem_sn=${clientid} limit 1
     */
    @Override
    public void connect() throws MqttException {
        String clientId = this.client.getClientId();
        String password = this.mqttProperties().getPassword();

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(this.mqttProperties().getUsername());
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(this.mqttProperties().getTimeout());
            options.setKeepAliveInterval(this.mqttProperties().getKeepalive());
            if (!this.client.isConnected()) {
                this.client.connect(options);
                log.info(" {}: {} 连接成功", this.iotServiceType(), clientId);
            } else {
                //这里的逻辑是如果连接成功就重新连接
                this.client.reconnect();
                log.info("{}: {} 重连接成功", this.iotServiceType(), clientId);
            }
            this.client.setCallback(this.callback());
        } catch (Exception e) {
            log.error("{}:{} 连接失败：{}", this.iotServiceType(), clientId, e.getMessage());
            throw e;
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    @Override
    public void subscribe(String topic, int qos) {
        try {
            this.client.subscribe(topic, qos);
            log.info("订阅主题: " + topic + " 成功");
        } catch (MqttException e) {
            e.printStackTrace();
            log.error("订阅主题: " + topic + " 失败");
        }
    }

    /**
     * 统一订阅
     */
    @Override
    public void subscribeAll() {
        if ("all".equals(this.serviceType.toLowerCase()) ||
                Objects.equals(this.iotServiceType().getVal(), this.serviceType.toLowerCase())) {
            log.info("当前服务类型 为 : 【{}】,开始 订阅服务", this.serviceType);
            this.mqttProperties().getSubscribeTopics().forEach(x -> {
                this.subscribe(x, this.mqttProperties().getQos());
            });
        } else {
            log.warn("当前服务类型 为 : 【{}】,发布类型不一致 无需订阅服务", this.serviceType);
        }
    }

    public MqttCallback callback() {
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                log.error("mqtt 断开连接 错误原因：{}", ThrowableUtil.stackTraceToString(throwable));
                // 连接丢失后，一般在这里面进行重连
                MqttClientReceiveAbstract.this.lostAndReconnect();
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                MqttClientReceiveAbstract.this.messageArrivedCallback(topic, mqttMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                log.debug("({}) mqtt deliveryComplete--------- {}", MqttClientReceiveAbstract.this.iotServiceType(), iMqttDeliveryToken.isComplete());
            }
        };
    }

    /**
     * 重连
     */
    public void lostAndReconnect() {
        log.info("连接断开，可以做重连");
        for (int i = 0; i < this.mqttProperties().getMaxReConnectNumbers(); i++) {
            try {
                Thread.sleep(5000);
                log.info("重试开始，{} 正在第 {} 次重试", this.client.getClientId(), i);
                if (!this.client.isConnected()) {
                    this.client.reconnect();
                } else {
                    log.info("***** connect success *****");
                    log.info("重连接成功 - 开始重新订阅");
                    // 订阅主题
                    this.subscribeAll();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("{} 连接失败,正在第" + i + "次尝试 {}", this.client.getClientId(), e.getMessage());
                try {
                    Thread.sleep(20000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        throw new RuntimeException("无法连接服务器");
    }
}
