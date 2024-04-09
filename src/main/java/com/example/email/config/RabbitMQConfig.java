package com.example.email.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue");
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("orderExchange");
    }

    @Bean
    public Binding binding(Queue notificationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with("orderQueue");
    }
}