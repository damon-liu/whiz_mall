package iot.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-07 11:22
 */
@Configuration
public class RabbitMqConfig {


    @Value("${ice.active}")
    private String env;

    @Bean(name = "rabbitConnectionFactory")
    @Primary
    public ConnectionFactory rabbitConnectionFactory(
            @Value("${spring.rabbitmq.addresses:}") String addresses,
            @Value("${spring.rabbitmq.host:}") String host,
            @Value("${spring.rabbitmq.port}") int port,
            @Value("${spring.rabbitmq.username:}") String username,
            @Value("${spring.rabbitmq.password:}") String password,
            @Value("${spring.rabbitmq.virtual-host:}") String virtualHost
    ) {
        return this.connectionFactory(addresses, host, port, username, password, virtualHost);
    }


    public CachingConnectionFactory connectionFactory(String addresses, String host, int port, String username, String password, String virtualHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean(name = "rabbitFactory")
    public SimpleRabbitListenerContainerFactory rbtFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory,
            @Value("${spring.rabbitmq.concurrency:3}") Integer concurrency,
            @Value("${spring.rabbitmq.prefetch:200}") Integer prefetch,
            @Value("${spring.rabbitmq.max-concurrency:5}") Integer maxConcurrency
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

    // /**
    //  * 由于多个rabbit 只能使用此方法
    //  *
    //  * @param connectionFactory
    //  * @return
    //  */
    // @Bean("rabbitAdmin4ice")
    // public RabbitAdmin rabbitAdmin(@Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory) {
    //     RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    //     // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
    //     rabbitAdmin.setAutoStartup(true);
    //
    //     //声明RabbitAdmin用户创建交换机，队列及绑定关系
    //     //创建交换机,并用rabbitAdmin进行声明
    //     TopicExchange fanoutExchange = new TopicExchange(this.env + EXCHANGE);
    //     rabbitAdmin.declareExchange(fanoutExchange);
    //     //创建队列,设置消息的持久化存储为true
    //     Queue queue = new Queue(this.env + QUEUE, true);
    //     //创建此队列与交换机的绑定关系,路由键为文章作者的id
    //     Binding bind = BindingBuilder.bind(queue).to(fanoutExchange).with(this.env + BINDING_KEY);
    //     //声明队列的使用
    //     rabbitAdmin.declareQueue(queue);
    //     //增加队列与交换机之间此作者id的路由键绑定
    //     rabbitAdmin.declareBinding(bind);
    //     return rabbitAdmin;
    // }
    //
    // @Bean(name = "rabbitTemplate")
    // @Primary
    // public RabbitTemplate rabbitTemplate(
    //         @Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory
    // ) {
    //     return new RabbitTemplate(connectionFactory);
    // }


    // @Component
    // class DmsRabbit {
    //     private static final String service = "dms";
    //     @Autowired
    //     @Qualifier("rabbitAdmin4ice")
    //     private RabbitAdmin rabbitAdmin;
    //
    //     @PostConstruct
    //     public void init() {
    //         RabbitMqConfig.this.createQueue(this.rabbitAdmin, service);
    //     }
    // }

    // @Component
    // class CrmRabbit {
    //     private static final String service = "crm";
    //     @Autowired
    //     @Qualifier("rabbitAdmin4ice")
    //     private RabbitAdmin rabbitAdmin;
    //
    //     @PostConstruct
    //     public void init() {
    //         RabbitMqConfig.this.createQueue(this.rabbitAdmin, service);
    //     }
    //
    // }
    //
    // public void createQueue(RabbitAdmin rabbitAdmin, String serviceName) {
    //     TopicExchange fanoutExchange = new TopicExchange(String.format(EXCHANGE_IOT_PUSH, this.env, serviceName));
    //     rabbitAdmin.declareExchange(fanoutExchange);
    //     //创建队列,设置消息的持久化存储为true
    //     Queue queue = new Queue(String.format(QUEUE_IOT_PUSH, this.env, serviceName), true);
    //     //创建此队列与交换机的绑定关系,路由键为文章作者的id
    //     Binding bind = BindingBuilder.bind(queue).to(fanoutExchange).with(String.format(QUEUE_IOT_PUSH, this.env, serviceName));
    //     //声明队列的使用
    //     rabbitAdmin.declareQueue(queue);
    //     //增加队列与交换机之间此作者id的路由键绑定
    //     rabbitAdmin.declareBinding(bind);
    // }
}