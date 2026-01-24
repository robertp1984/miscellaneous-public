package org.softwarecave.springbootmqreceiver.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageMessage {
    private String id;
    private String originalFilename;
    private String contentType;
    private Instant createdTime;
}
