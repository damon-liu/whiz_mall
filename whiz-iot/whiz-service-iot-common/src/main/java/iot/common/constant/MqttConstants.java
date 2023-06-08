package iot.common.constant;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-08 3:14
 */
public interface MqttConstants {


    String ICER_TOPIC_QUEUE = ".iot.topic.l";

    String ICER_TOPIC_EXCHANGE = ".iot.topic.e";

    String ICER_TOPIC_BINDING_KEY = ".iot.topic.l";

    String ICER_IOT_PUSH_EXCHANGE = "%s.iot.topic.push.e.%s";

    String ICER_IOT_PUSH_QUEUE = "%s.iot.topic.push.q.%s";

}
