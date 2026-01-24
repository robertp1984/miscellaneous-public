package org.softwarecave.springbootimages.bedrock;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.softwarecave.springbootimages.images.Image;
import org.softwarecave.springbootimages.images.ImageBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

@Service
@Transactional
@Slf4j
public class ImageGenerationService {

    private final String imageGenModel = "amazon.titan-image-generator-v2:0";
    private final String imageMediaType = MediaType.IMAGE_PNG_VALUE;
    private final int maxFilenameLength = 128;

    private final ObjectMapper objectMapper;

    public ImageGenerationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Image generateImageByDescription(@NonNull String description) {
        String jsonRequest = createRequest(description);

        try (BedrockRuntimeClient client = createClient()) {

            var response = client.invokeModel(request -> request.body(SdkBytes.fromUtf8String(jsonRequest))
                    .modelId(imageGenModel)
                    .accept(imageMediaType));

            return parseResponse(description, response);
        } catch (Exception e) {
            log.error("Failed to generate image {} ", e.getMessage(), e);
            throw new ImageGenerationException("Could not generate image with description=%s".formatted(description), e);
        }
    }


    private String createRequest(String description) {
        var requestTemplate = """
                {
                    "taskType": "TEXT_IMAGE",
                    "textToImageParams": { "text": "{{prompt}}" },
                    "imageGenerationConfig": { "seed": {{seed}} }
                }""";

        var seed = new BigInteger(31, new SecureRandom());

        return requestTemplate
                .replace("{{prompt}}", description)
                .replace("{{seed}}", seed.toString());
    }

    private BedrockRuntimeClient createClient() {
        return BedrockRuntimeClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .region(Region.US_EAST_1)
                .build();
    }

    private Image parseResponse(String description, InvokeModelResponse response) throws IOException {
        var responseBodyBytes = response.body().asByteArray();
        BedrockImageBodyResponse responseObject = objectMapper.readValue(responseBodyBytes, BedrockImageBodyResponse.class);

        if (responseObject != null && responseObject.hasImage()) {
            return new ImageBuilder()
                    .withOriginalFilename(createShortFilename(description))
                    .withBytes(responseObject.getFirstImageBytes())
                    .withUUID()
                    .withContentType(imageMediaType)
                    .withCurrentDateTime()
                    .build();
        } else {
            throw new ImageGenerationException("Failed to generate image. No image present", null);
        }
    }

    private String createShortFilename(String description) {
        String extension = getShortFilenameExtension();
        String baseName = description
                .replace(" ", "_")
                .substring(0, Math.min(maxFilenameLength - extension.length(), description.length()));
        return baseName + "." + extension;
    }

    private String getShortFilenameExtension() {
        if (imageMediaType.equals(MediaType.IMAGE_PNG_VALUE)) {
            return "png";
        } else {
            throw new IllegalArgumentException("Unsupported image media type " + imageMediaType);
        }
    }
}
