package io.web.bi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMqConfig.
 *
 * @date 2023-06-16
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 问答队列
     */
    @Bean
    public Queue qaQueue() {
        return new Queue("qaQueue", true);
    }

    /**
     * 交换机
     */
    @Bean
    DirectExchange qaDirectExchange() {
        return new DirectExchange("qaDirectExchange", true, false);
    }

    /**
     * 绑定队列和交换机,routingkey = qaRouting
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(qaQueue()).to(qaDirectExchange()).with("qaRouting");
    }
}
