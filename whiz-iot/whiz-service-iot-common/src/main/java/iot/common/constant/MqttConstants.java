package iot.common.constant;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-08 3:14
 */
public interface MqttConstants {


    String ICER_TOPIC_QUEUE = ".icer.topic.queue";

    String ICER_TOPIC_EXCHANGE = ".icer.topic.exchange";

    String ICER_TOPIC_BINDING_KEY = ".icer.topic.bindKey";

    String ICER_IOT_PUSH_EXCHANGE = "%s.iot.topic.push.e.%s";

    String ICER_IOT_PUSH_QUEUE = "%s.iot.topic.push.q.%s";

    String RBT_TOPIC_EXCHANGE = "rbt.topic.exchange";

    String RBT_TOPIC_QUEUE = "rbt.topic.queue.";

}
