package iot.config;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import iot.common.constant.MqttConstants;
import iot.common.entity.RbtMqMessage;
import iot.resolver.ArgumentResolverHeader;
import iot.resolver.ArgumentResolverHeader4A;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 15:04
 */
@Service
@Slf4j
public class RbtRabbitReceiver {

    public static final String PRE_SYS = "$SYS/";

    public static final String TEST_TOPIC = "/test";

    private static final String ENCODING = Charset.defaultCharset().name();
    private static final Set<String> whiteListPatterns = new LinkedHashSet(Arrays.asList("java.util.*", "java.lang.*"));

    public static List<ArgumentResolverHeader> resolver4HeaderList = new ArrayList<>();

    static {
        resolver4HeaderList.add(new ArgumentResolverHeader4A());
    }

    @RabbitHandler
    @RabbitListener(queues = MqttConstants.RBT_TOPIC_QUEUE + "${ice.active}" , containerFactory = "rbtFactory")
    public void onMessage(@Payload RbtMqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
        String topic = obj.getTopic();
        Object parse = JSONObject.parse(obj.getPayload());
        log.info("【rbt rabbitMq】 接收topic：{} 消息：{} ", topic, parse);
    }
}
