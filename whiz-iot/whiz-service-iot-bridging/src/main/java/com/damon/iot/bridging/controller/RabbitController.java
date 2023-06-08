package com.damon.iot.bridging.controller;

import com.damon.iot.bridging.utils.RabbitSendMsgUtil;
import iot.common.entity.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 11:47
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private RabbitSendMsgUtil rabbitMqUtils;


    /**
     * 列表数据
     */
    @GetMapping("/sendMsg")
    public String sendMsg(@RequestParam String type,
                          @RequestParam String exchangeName,
                          @RequestParam String routingKey,
                          @RequestParam String expireTime,
                          @RequestParam String msg) {
        MqMessage message = MqMessage.builder()
                .topic("topic/test")
                .serverRecvTime(System.currentTimeMillis())
                .remark(msg)
                .build();
        switch (type) {
            case "work":
                rabbitMqUtils.asyncSend("test.work", message);
                break;
            case "ttl":
                rabbitMqUtils.asyncSend4Ttl(exchangeName, routingKey, msg, expireTime);
                break;
            case "confirm":
                rabbitMqUtils.asyncSend4Confirm(exchangeName, routingKey, msg);
                break;
            case "return":
                rabbitMqUtils.asyncSend4Return(exchangeName, routingKey, msg);
                break;
            case "cr":
                rabbitMqUtils.asyncSend4ConfirmAndReturn(exchangeName, routingKey, msg);
                break;
            default:
                rabbitMqUtils.asyncSend(exchangeName, routingKey, msg);
        }
        return msg;
    }

}
