package org.softwarecave.springbootmqreceiver.messaging;

import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageMessageServiceTest {

    @InjectMocks
    private ImageMessageService imageMessageService;

    @Mock
    private MongoOperations mongoOperations;

    private ImageMessage msg = new ImageMessage("id", "file1.txt", "plain/text", Instant.now());

    @Test
    void savesImageMessageDelegatesToMongoOperations() {
        doReturn(msg).when(mongoOperations).insert(msg, ImageMessageService.IMAGE_MESSAGE_COLLECTION);

        imageMessageService.save(msg);

        verify(mongoOperations).insert(msg, ImageMessageService.IMAGE_MESSAGE_COLLECTION);
    }

    @Test
    void returnsAllImageMessagesWhenGetAllCalled() {
        ImageMessage msg1 = new ImageMessage("id", "file1.txt", "plain/text", Instant.now());
        ImageMessage msg2 = new ImageMessage("id", "file2.txt", "plain/text", Instant.now());
        List<ImageMessage> messages = List.of(msg1, msg2);
        when(mongoOperations.findAll(ImageMessage.class, ImageMessageService.IMAGE_MESSAGE_COLLECTION)).thenReturn(messages);

        List<ImageMessage> result = imageMessageService.getAll();

        assertSame(messages, result);
    }

    @Test
    void returnsImageMessageWhenFoundById() {
        when(mongoOperations.findById("non-existent", ImageMessage.class, ImageMessageService.IMAGE_MESSAGE_COLLECTION)).thenReturn(null);

        ImageMessage result = imageMessageService.findById("non-existent");
        assertNull(result);
    }

    @Test
    void throwsNoSuchElementExceptionWhenRemoveByIdDeletesNone() {
        DeleteResult deleteResult = mock(DeleteResult.class);
        when(mongoOperations.remove(any(Query.class), eq(ImageMessage.class), eq(ImageMessageService.IMAGE_MESSAGE_COLLECTION))).thenReturn(deleteResult);
        when(deleteResult.getDeletedCount()).thenReturn(0L);

        assertThrows(NoSuchElementException.class, () -> imageMessageService.removeById("nonexistent-id"));
    }

    @Test
    void doesNotThrowWhenRemoveByIdDeletesOne() {
        DeleteResult deleteResult = mock(DeleteResult.class);
        when(mongoOperations.remove(any(Query.class), eq(ImageMessage.class), eq(ImageMessageService.IMAGE_MESSAGE_COLLECTION))).thenReturn(deleteResult);
        when(deleteResult.getDeletedCount()).thenReturn(1L);

       imageMessageService.removeById("correct-id");

        verify(mongoOperations).remove(any(Query.class), eq(ImageMessage.class), eq(ImageMessageService.IMAGE_MESSAGE_COLLECTION));
    }


}
