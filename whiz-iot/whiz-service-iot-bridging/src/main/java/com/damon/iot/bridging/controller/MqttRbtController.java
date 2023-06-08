package com.damon.iot.bridging.controller;

import com.damon.iot.bridging.config.mqtt.publish.MqttPublish4Rbt;
import com.damon.iot.bridging.entity.PublishMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-05 6:13
 */

@RestController
@RequestMapping("/mqtt/rbt")
public class MqttRbtController {

    @Autowired
    private MqttPublish4Rbt mqttPublish;

    @PostMapping(value = "/publish")
    public void publishMsg(@RequestBody PublishMsg publishMsg) {
        mqttPublish.publish(publishMsg.getQos(), publishMsg.getRetained(), publishMsg.getTopic(), publishMsg.getMsg());
    }

    @PostMapping(value = "/publishByDefaultConfig")
    public void getAttachInfo(@RequestBody PublishMsg publishMsg) {
        mqttPublish.publishByDefaultConfig(publishMsg.getTopic(), publishMsg.getMsg());
    }

}
