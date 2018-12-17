package com.bookool.myboot.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit 消息配置，Exchange Types
 *
 * @author Tommy
 */
@Configuration
public class RabbitConfig {

    public static final String QUEUE_USER = "user";

    private static final String EXCHANGE_USER = "userExchange";

    @Bean
    public Queue queueUser() {
        return new Queue(QUEUE_USER);
    }

    @Bean
    FanoutExchange exchangeUser() {
        return new FanoutExchange(EXCHANGE_USER);
    }

    @Bean
    Binding bindingExchangeUser(Queue queueUser, FanoutExchange exchangeUser) {
        return BindingBuilder.bind(queueUser).to(exchangeUser);
    }

}
