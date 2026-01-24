package org.softwarecave.springbootmqreceiver.messaging;

import lombok.NonNull;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ImageMessageService {

    public static final String IMAGE_MESSAGE_COLLECTION = "imageMessage";

    private final MongoOperations mongoOperations;

    public ImageMessageService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public ImageMessage save(@NonNull ImageMessage imageMessage) {
        return mongoOperations.insert(imageMessage, IMAGE_MESSAGE_COLLECTION);
    }

    public List<ImageMessage> getAll() {
        return mongoOperations.findAll(ImageMessage.class, IMAGE_MESSAGE_COLLECTION);
    }

    public ImageMessage findById(@NonNull String id) {
        return mongoOperations.findById(id, ImageMessage.class, IMAGE_MESSAGE_COLLECTION);
    }

    public void removeById(@NonNull String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        var result = mongoOperations.remove(query, ImageMessage.class, IMAGE_MESSAGE_COLLECTION);
        if (result.getDeletedCount() == 0) {
            throw new NoSuchElementException("No ImageMessage with id=" + id);
        }
    }

    public List<ImageMessage> getAllBeforeCreatedDateTime(Instant createdTime) {
        //TODO:
        Query query = new Query();
        query.addCriteria(Criteria.where("createdDateTime").gte(createdTime));
        return mongoOperations.find(query, ImageMessage.class, IMAGE_MESSAGE_COLLECTION);
    }

    public List<ImageMessage> getAllCreatedToday() {
        //TODO:
        ZonedDateTime zonedDateTime = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return getAllBeforeCreatedDateTime(zonedDateTime.toInstant());
    }
}
