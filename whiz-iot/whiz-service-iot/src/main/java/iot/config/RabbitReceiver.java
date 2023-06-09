package iot.config;

import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import iot.common.constant.MqttConstants;
import iot.common.entity.MqMessage;
import iot.entity.TopicHeader;
import iot.entity.TopicRequest;
import iot.resolver.ArgumentResolverHeader;
import iot.resolver.ArgumentResolverHeader4A;
import iot.utils.HexResolverUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static final String TEST_TOPIC = "/test";

    public static List<ArgumentResolverHeader> resolver4HeaderList = new ArrayList<>();

    static {
        resolver4HeaderList.add(new ArgumentResolverHeader4A());
    }


    @RabbitHandler
    @RabbitListener(queues = "${ice.active}" + MqttConstants.ICER_TOPIC_QUEUE, containerFactory = "rabbitFactory")
    public void onMessage(@Payload MqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
        // 系统订阅
        byte[] payload = obj.getPayload();
        String topic = obj.getTopic();
        log.info("【icer rabbitMq】 接收topic：{} ", topic);
        if (obj.getTopic().contains(PRE_SYS) || topic.contains(TEST_TOPIC)) {
            Object parse = JSONObject.parse(payload);
            log.info("消息体：{}", parse);
        } else {
            // 转为16进制
            String modemSn = this.getTopicModemSn(topic);
            String hexStr = HexUtil.encodeHexStr(payload).toUpperCase();
            log.info("topic:{} -> HEX:{}", topic, hexStr);
            // 数据包转换
            char[] bodyChars = hexStr.toCharArray();
            List<String> hexArray = this.char2hexArray(bodyChars);
            log.info("hexArray: {} ", hexArray);
            // 路由 旧协议还是新协议
            Optional<ArgumentResolverHeader> first = resolver4HeaderList.stream().filter(x -> x.uploadShould(hexArray)).findFirst();
            ArgumentResolverHeader argumentResolverHeader = first.orElseGet(ArgumentResolverHeader4A::new);
            // 报文解析 转换为header
            TopicHeader header = argumentResolverHeader.resolveArgument(null, hexArray);
            TopicRequest request = new TopicRequest(header, hexStr, hexArray);
            log.info("request:  {}", request);
        }

    }

    /**
     * /thing/resource/icer_i18/40016022270016/post_reply hex
     *
     * @param topic
     * @return
     */
    private String getTopicModemSn(String topic) {
        String tTopic = topic;
        // 去除首尾 /
        tTopic = tTopic.endsWith("/") ? topic.substring(0, topic.length() - 2) : topic;
        String[] split = tTopic.split("/");
        return split[split.length - 2];
    }

    /**
     * 判定是否走此路由
     *
     * @param hexArray
     * @return
     */
    private Integer type(List<String> hexArray) {
        HexResolverUtil resolver = new HexResolverUtil(hexArray);
        int type = resolver.resolver2Int(4, 6);
        return type;
    }

    // @RabbitHandler
    // @RabbitListener(queues = "test.work" , containerFactory = "rabbitFactory")
    // public void testOnMessage(@Payload MqMessage obj, Message message, Channel channel, @Headers Map<String, Object> headers) {
    //     // 系统订阅
    //     log.info("【ICER】 测试消息 开始执行：{}", obj);
    // }

    /**
     * char 转为 16进制的数组
     *
     * @param bodyChars
     * @return
     */
    private List<String> char2hexArray(char[] bodyChars) {
        List<String> hexList = Lists.newArrayList();
        for (int i = 0; i < bodyChars.length - 1; i++) {
            if (i % 2 == 0) {
                hexList.add(String.valueOf(new char[]{bodyChars[i], bodyChars[i + 1]}));
            }
        }
        return hexList;
    }

}
