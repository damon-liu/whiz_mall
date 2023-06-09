package iot.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
@ConditionalOnProperty(prefix = "spring.rbtRabbitmq", name = "enable", matchIfMissing = false)
public class RbtRabbitConfig {

    @Value("${ice.active}")
    private String env;

    /**
     * 定义连接，我方rabbitMQ
     */
    @Bean(name = "rbtConnectionFactory")
    public ConnectionFactory rbtConnectionFactory(
            @Value("${spring.rbtRabbitmq.host}") String host,
            @Value("${spring.rbtRabbitmq.port}") int port,
            @Value("${spring.rbtRabbitmq.username}") String username,
            @Value("${spring.rbtRabbitmq.password}") String password,
            @Value("${spring.rbtRabbitmq.virtual-host:}") String virtualHost
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
            @Value("${spring.rbtRabbitmq.concurrency:10}") Integer concurrency,
            @Value("${spring.rbtRabbitmq.prefetch:200}") Integer prefetch,
            @Value("${spring.rbtRabbitmq.max-concurrency:5}") Integer maxConcurrency
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 客户端缓存数量
        factory.setPrefetchCount(prefetch);
        //设置线程数
        factory.setConcurrentConsumers(concurrency);
        //最大线程数
        factory.setMaxConcurrentConsumers(concurrency * 3);
        factory.setMissingQueuesFatal(false);
        return factory;
    }
}
