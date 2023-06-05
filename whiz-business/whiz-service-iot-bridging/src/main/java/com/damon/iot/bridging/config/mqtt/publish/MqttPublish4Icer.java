package com.damon.iot.bridging.config.mqtt.publish;

import com.damon.iot.bridging.config.mqtt.properties.MqttProperties;
import com.damon.iot.bridging.config.mqtt.properties.MqttProperties4Icer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-05 6:16
 */

@Service
@ConditionalOnProperty(prefix = "spring.mqtt", name = "enable", matchIfMissing = true)
public class MqttPublish4Icer extends MqttPublishAbstract{

    @Autowired
    private MqttProperties4Icer properties;

    @Autowired
    @Qualifier("icerMqttClient")
    private MqttClient mqttClient;

    @Override
    public MqttClient client() {
        return this.mqttClient;
    }

    @Override
    public MqttProperties properties() {
        return this.properties;
    }
}
