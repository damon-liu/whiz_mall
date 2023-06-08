package com.damon.iot.bridging.config.mqtt;

import com.damon.iot.bridging.config.mqtt.receive.MqttClientReceiveAbstract;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-05 2:14
 */
@Service
@Slf4j
public class MqttConnectConfig implements CommandLineRunner {

    @Autowired
    private List<MqttClientReceiveAbstract> mqttReceiveClientList;

    @Override
    public void run(String... args) {
        // 链接 mqtt 完成
        this.mqttReceiveClientList.forEach(x -> {
            try {
                x.connect();
                x.subscribeAll();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

}
