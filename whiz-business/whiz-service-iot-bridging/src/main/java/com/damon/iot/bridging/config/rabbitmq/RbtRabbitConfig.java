package com.damon.iot.bridging.config.rabbitmq;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 14:59
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq.rbt", name = "enable", matchIfMissing = false)
public class RbtRabbitConfig {

    @Value("${ice.active}")
    private String env;

    public static final String RBT_TOPIC_EXCHANGE = "rbt.exchange.test";

    public static final String RBT_TOPIC_QUEUE = "rbt.queue.test.";


    /**
     * 定义连接，我方rabbitMQ
     */
    @Bean(name = "rbtConnectionFactory")
    public ConnectionFactory rbtConnectionFactory(
            @Value("${spring.rabbitmq.rbt.host}") String host,
            @Value("${spring.rabbitmq.rbt.port}") int port,
            @Value("${spring.rabbitmq.rbt.username}") String username,
            @Value("${spring.rabbitmq.rbt.password}") String password,
            @Value("${spring.rabbitmq.rbt.virtual-host:}") String virtualHost
    ) {
        return this.connectionFactory(host, port, username, password, virtualHost);
    }


    public CachingConnectionFactory connectionFactory(String host, int port, String username, String password, String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        if (StringUtils.isNotBlank(virtualHost)) {
            connectionFactory.setVirtualHost(virtualHost);
        }
        return connectionFactory;
    }


    @Bean(name = "rbtRabbitTemplate")
    public RabbitTemplate rbtRabbitTemplate(
            @Qualifier("rbtConnectionFactory") ConnectionFactory connectionFactory
    ) {
        RabbitTemplate rbtRabbitTemplate = new RabbitTemplate(connectionFactory);
        return rbtRabbitTemplate;
    }

    @Bean(name = "rbtFactory")
    public SimpleRabbitListenerContainerFactory rbtFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("rbtConnectionFactory") ConnectionFactory connectionFactory,
            @Value("${spring.rabbitmq.rbt.concurrency}") Integer concurrency,
            @Value("${spring.rabbitmq.rbt.prefetch}") Integer prefetch,
            @Value("${spring.rabbitmq.rbt.max-concurrency}") Integer maxConcurrency
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 客户端缓存数量
        factory.setPrefetchCount(prefetch);
        //设置线程数
        factory.setConcurrentConsumers(concurrency);
        //最大线程数
        factory.setMaxConcurrentConsumers(maxConcurrency);
//        factory.setRetryTemplate(attemptsRetry());
        factory.setMissingQueuesFatal(false);
        return factory;
    }


    @Bean
    //@ConditionalOnProperty(prefix = "ice.iot.pubRbtMess", name = "enable", matchIfMissing = true)
    public RabbitAdmin rabbitAdmin(@Qualifier("rbtConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);

        //声明RabbitAdmin用户创建交换机，队列及绑定关系
        //创建交换机,并用rabbitAdmin进行声明
        FanoutExchange fanoutExchange = new FanoutExchange(RBT_TOPIC_EXCHANGE);
        rabbitAdmin.declareExchange(fanoutExchange);
        //创建队列,设置消息的持久化存储为true
        Queue queue = new Queue(RBT_TOPIC_QUEUE + this.env, true);
        //创建此队列与交换机的绑定关系,路由键为文章作者的id
        Binding bind = BindingBuilder.bind(queue).to(fanoutExchange);
        //声明队列的使用
        rabbitAdmin.declareQueue(queue);
        //增加队列与交换机之间此作者id的路由键绑定
        rabbitAdmin.declareBinding(bind);
        return rabbitAdmin;
    }

}
