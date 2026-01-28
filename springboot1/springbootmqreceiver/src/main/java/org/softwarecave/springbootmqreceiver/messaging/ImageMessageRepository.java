package org.softwarecave.springbootmqreceiver.messaging;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMessageRepository extends MongoRepository<ImageMessage, String> {
}
