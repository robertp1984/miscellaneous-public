package org.softwarecave.springbootimages.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessagingConfig {

    public static final String QUEUE_NAME = "images";
    public static final String EXCHANGE_NAME = "operations";
    public static final String ROUTING_KEY = "images.#";

    @Bean
    public Queue imagesQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public TopicExchange operationsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding operationsImagesBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

}
