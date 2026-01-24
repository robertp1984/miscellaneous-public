package org.softwarecave.springbootmqreceiver.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ImageMessageListenerTest {

    @InjectMocks
    private ImageMessageListener imageMessageListener;

    @Mock
    private ImageMessageProcessor imageMessageProcessor;


    private static final String FILENAME1 = "filename1.txt";
    private static final String TEXT_PLAIN = "text/plain";
    private static final Instant INSTANT = Instant.now();


    @Test
    public void testReceiveMessage_Null() {
        assertThrows(NullPointerException.class, () -> imageMessageListener.receiveMessage(null));
    }

    @Test
    public void testReceiveMessage_Empty() {
        assertThrows(IllegalArgumentException.class, () -> imageMessageListener.receiveMessage(""));
    }

    @Test
    public void testReceiveMessage_InvalidJson() {
        assertThrows(IllegalArgumentException.class, () -> imageMessageListener.receiveMessage("{aa:22}"));
    }

    @Test
    public void testReceiveMessage_ValidAndComplete() throws JsonProcessingException {
        // given
        ImageMessage sourceImageMessage = new ImageMessage(UUID.randomUUID().toString(),
                FILENAME1, TEXT_PLAIN, INSTANT);
        String sourceImageMessageJson = getObjectMapper().writeValueAsString(sourceImageMessage);
        doNothing().when(imageMessageProcessor).process(sourceImageMessage);

        // when
        imageMessageListener.receiveMessage(sourceImageMessageJson);

        // then
        ArgumentCaptor<ImageMessage> captor = ArgumentCaptor.forClass(ImageMessage.class);
        verify(imageMessageProcessor, times(1)).process(captor.capture());
        ImageMessage capturedImageMessage = captor.getValue();
        assertThat(capturedImageMessage)
                .hasFieldOrPropertyWithValue("originalFilename", FILENAME1)
                .hasFieldOrPropertyWithValue("contentType", TEXT_PLAIN)
                .hasFieldOrPropertyWithValue("createdTime", INSTANT);
    }

    @Test
    public void testReceiveMessage_ValidAndNotComplete() throws JsonProcessingException {
        // given
        ImageMessage sourceImageMessage = new ImageMessage(UUID.randomUUID().toString(),
                FILENAME1, TEXT_PLAIN, null);
        String sourceImageMessageJson = getObjectMapper().writeValueAsString(sourceImageMessage);
        doNothing().when(imageMessageProcessor).process(sourceImageMessage);

        // when
        imageMessageListener.receiveMessage(sourceImageMessageJson);

        // then
        ArgumentCaptor<ImageMessage> captor = ArgumentCaptor.forClass(ImageMessage.class);
        verify(imageMessageProcessor, times(1)).process(captor.capture());
        ImageMessage capturedImageMessage = captor.getValue();
        assertThat(capturedImageMessage)
                .hasFieldOrPropertyWithValue("originalFilename", FILENAME1)
                .hasFieldOrPropertyWithValue("contentType", TEXT_PLAIN)
                .hasFieldOrPropertyWithValue("createdTime", null);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
