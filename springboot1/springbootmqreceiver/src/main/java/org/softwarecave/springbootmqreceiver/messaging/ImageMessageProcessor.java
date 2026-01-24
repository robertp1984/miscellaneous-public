package org.softwarecave.springbootmqreceiver.messaging;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ImageMessageProcessor {

    private final ImageMessageService imageMessageService;

    public ImageMessageProcessor(ImageMessageService imageMessageService) {
        this.imageMessageService = imageMessageService;
    }

    public void process(@NonNull ImageMessage imageMessage) {
        log.info("Received an image message {}", imageMessage);
        imageMessageService.save(imageMessage);
    }
}
