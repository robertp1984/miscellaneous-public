package org.softwarecave.springbootimages.images;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import org.softwarecave.springbootimages.bedrock.ImageGenerationService;
import org.softwarecave.springbootimages.messaging.ImageMessageFactory;
import org.softwarecave.springbootimages.messaging.QueueSender;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Scope("singleton")
public class ImageService {
    private final ImageRepository imageRepository;
    private final QueueSender queueSender;
    private final ImageGenerationService imageGenerationService;

    public ImageService(ImageRepository imageRepository, QueueSender queueSender,
                        ImageGenerationService imageGenerationService) {
        this.imageRepository = imageRepository;
        this.queueSender = queueSender;
        this.imageGenerationService = imageGenerationService;
    }

    public void saveImage(@NonNull Image image) throws JsonProcessingException {
        imageRepository.save(image);
        queueSender.publishImagesSavedMessage(ImageMessageFactory.createImageMessage(image));
    }

    public Optional<Image> getImage(@NonNull String id) {
        return imageRepository.findById(id);
    }

    public void deleteImage(@NonNull String id) {
        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
        } else {
            throw new NoSuchImageException("Image with id: " + id + " does not exist");
        }
    }

    public Image generateAndSaveImageByDescription(@NonNull String description) {
        Image image = imageGenerationService.generateImageByDescription(description);
        imageRepository.save(image);
        return image;
    }
}
