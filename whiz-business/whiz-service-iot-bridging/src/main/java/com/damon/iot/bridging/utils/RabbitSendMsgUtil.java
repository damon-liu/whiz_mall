package com.damon.iot.bridging.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 11:27
 */
@Component
@Slf4j
public class RabbitSendMsgUtil implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource
    private ConnectionFactory connectionFactory;

    /**
     * 消息发送到exchange后回调confirm方法
     *
     * @param correlationData 相关配置信息
     * @param ack             exchange交换机 是否成功收到了消息。true 成功，false代表失败
     * @param cause           失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("confirm方法被执行了....");

        //ack 为true表示 消息已经到达交换机
        if (ack) {
            log.info("交换机接收成功消息：{}", cause);
        } else {
            log.warn("交换机接收失败消息：{}", cause);
            //做一些处理，让消息再次发送。
        }
    }

    /**
     * 消息从exchange路由到queue失败回调
     *
     * @param message    消息对象
     * @param replyCode  错误码
     * @param replyText  错误信息
     * @param exchange   交换机
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.warn("return 执行, 消息投递至队列失败....");
        log.info("message: {}", message);
        log.info("replyCode: {}, replyText: {}, exchange: {}, routingKey: {}", replyCode, replyText, exchange, routingKey);
    }

    public void asyncSend(String queue, Object context) {
        String corrId = UuidUtils.getUuid();
        CorrelationData correlationData = new CorrelationData(corrId);
        rabbitTemplate.convertAndSend(queue, context, correlationData);
    }

    public void asyncSend(RabbitTemplate customRabbitTemplate, String queue, Object context) {
        String corrId = UuidUtils.getUuid();
        CorrelationData correlationData = new CorrelationData(corrId);
        customRabbitTemplate.convertAndSend(queue, context, correlationData);
    }


    /**
     * 通用异步消息发送
     *
     * @param context      消息内容
     * @param routingKey   交换机
     * @param exchangeName 交换机
     */
    public void asyncSend(String exchangeName, String routingKey, Object context) {
        String corrId = UuidUtils.getUuid();
        CorrelationData correlationData = new CorrelationData(corrId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, context, correlationData);
    }

    public void asyncSend(String exchangeName, String routingKey, Object context, RabbitTemplate rabbitTemplateScope) {
        String corrId = UuidUtils.getUuid();
        CorrelationData correlationData = new CorrelationData(corrId);
        rabbitTemplateScope.convertAndSend(exchangeName, routingKey, context, correlationData);
    }

    public void asyncSend4Ttl(String exchangeName, String routingKey, Object context, String expireTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String corrId = UuidUtils.getUuid();
        CorrelationData correlationData = new CorrelationData(corrId);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, context,
                message -> {
                    // 设置5秒过期
                    message.getMessageProperties().setExpiration(expireTime);
                    return message;
                }, correlationData);
        log.info("开始发送延迟消息：{}， 时间：{}", context, sdf.format(new Date()));
    }

    public void asyncSend4Confirm(String exchangeName, String routingKey, Object context) {
        RabbitTemplate rabbitTemplateScope = new RabbitTemplate(connectionFactory);
        rabbitTemplateScope.setConfirmCallback(this);
        asyncSend(exchangeName, routingKey, context, rabbitTemplateScope);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("开始发送队列确认消息：{}， 时间：{}", context, sdf.format(new Date()));
    }

    public void asyncSend4Return(String exchangeName, String routingKey, Object context) {
        RabbitTemplate rabbitTemplateScope = new RabbitTemplate(connectionFactory);

        //设置交换机处理失败消息的模式  为true的时候，消息达到不了 队列时，会将消息重新返回给生产者
        rabbitTemplateScope.setMandatory(true);
        rabbitTemplateScope.setReturnCallback(this);

        asyncSend(exchangeName, routingKey, context, rabbitTemplateScope);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("开始发送队列确认消息：{}， 时间：{}", context, sdf.format(new Date()));
    }

    public void asyncSend4ConfirmAndReturn(String exchangeName, String routingKey, Object context) {
        RabbitTemplate rabbitTemplateScope = new RabbitTemplate(connectionFactory);
        //设置交换机处理失败消息的模式  为true的时候，消息达到不了 队列时，会将消息重新返回给生产者
        rabbitTemplateScope.setMandatory(true);
        rabbitTemplateScope.setReturnCallback(this);
        rabbitTemplateScope.setConfirmCallback(this);
        asyncSend(exchangeName, routingKey, context, rabbitTemplateScope);
    }

}
