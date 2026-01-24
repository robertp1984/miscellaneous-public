package org.softwarecave.springbootmqreceiver.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.softwarecave.springbootmqreceiver.messaging.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Slf4j
public class ImageMessageListener {

    private final ImageMessageProcessor imageMessageProcessor;

    public ImageMessageListener(ImageMessageProcessor imageMessageProcessor) {
        this.imageMessageProcessor = imageMessageProcessor;
    }

    @RabbitListener(queues = MessagingConfig.IMAGES_QUEUE_NAME)
    public void receiveMessage(@NonNull String messageString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            ImageMessage imageMessage = objectMapper.readValue(messageString, ImageMessage.class);
            imageMessageProcessor.process(imageMessage);

        } catch (JsonProcessingException e) {
            log.error("Failed to read or process the received message: %s".formatted(e.getMessage()), e);
            throw new IllegalArgumentException("Failed to read or process the received message", e);
        }
    }
}
