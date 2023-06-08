package com.damon.iot.bridging.controller;

import com.damon.iot.bridging.config.mqtt.publish.MqttPublish4Icer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-05 6:13
 */

@RestController
@RequestMapping("/mqtt/icer")
public class MqttIcerController {

    @Autowired
    private MqttPublish4Icer mqttPublishMessage4Icer;


}
