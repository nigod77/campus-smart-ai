package com.nijiahao.llm.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // 1. 定义名称
    public static final String EXCHANGE_NAME = "rag.direct.exchange"; // 交换机
    public static final String QUEUE_NAME = "doc.parse.queue";        // 队列
    public static final String ROUTING_KEY = "doc.parse.key";         // 路由键

    //队列
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME , true);
    }

    //交换机
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    //绑定
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
