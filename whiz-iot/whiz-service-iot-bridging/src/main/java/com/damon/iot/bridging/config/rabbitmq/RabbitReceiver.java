package com.damon.iot.bridging.config.rabbitmq;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 15:04
 */
// @Service
// @Slf4j
// public class RabbitReceiver {
//
//     public static final String PRE_SYS = "$SYS/";
//
//
//     @RabbitHandler
//     @RabbitListener(queues = "${ice.active}" + RabbitMqConfig.QUEUE, containerFactory = "rabbitFactory")
//     public void onMessage(@Payload MqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
//         // 系统订阅
//         log.info("【icer rabbitMq】 接收topic：{}", obj.getTopic());
//         if (obj.getTopic().contains(PRE_SYS)) {
//             Object parse = JSONObject.parse(obj.getPayload());
//             log.info("消息体：{}", parse);
//         } else {
//
//         }
//     }
//
//     @RabbitHandler
//     @RabbitListener(queues = "test.work" , containerFactory = "rabbitFactory")
//     public void testOnMessage(@Payload MqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
//         // 系统订阅
//         log.info("【ICER】 测试消息 开始执行：{}", obj);
//     }
//
// }
