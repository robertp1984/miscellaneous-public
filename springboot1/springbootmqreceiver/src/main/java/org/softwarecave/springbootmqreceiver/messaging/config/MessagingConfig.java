package org.softwarecave.springbootmqreceiver.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessagingConfig {

    public static final String IMAGES_QUEUE_NAME = "images";

    @Bean
    public Queue imagesQueue() {
        return new Queue(IMAGES_QUEUE_NAME);
    }

}
