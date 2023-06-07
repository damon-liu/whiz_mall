package com.damon.iot.bridging.config.rabbitmq;

import com.damon.iot.bridging.entity.dto.MqMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 15:04
 */
@Service
@Slf4j
public class RabbitReceiver {

    public static final String PRE_SYS = "$SYS/";


    @RabbitHandler
    @RabbitListener(queues = "${ice.active}" + RabbitMqConfig.QUEUE, containerFactory = "rabbitFactory")
    public void onMessage(@Payload MqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
        // 系统订阅
        if (obj.getTopic().contains(PRE_SYS)) {
            log.info("【ICER】 系统消息 开始执行：{}", obj);
        } else {
            log.info("【ICER】 非系统消息 开始执行：{}", obj);
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "test.work" , containerFactory = "rabbitFactory")
    public void testOnMessage(@Payload MqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
        // 系统订阅
        log.info("【ICER】 测试消息 开始执行：{}", obj);
    }

}
