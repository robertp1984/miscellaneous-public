package org.softwarecave.springbootimages.bedrock;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.softwarecave.springbootimages.images.Image;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
@Transactional
@Slf4j
public class ImageGenerationService {

    private final String IMAGE_GEN_MODEL = "amazon.titan-image-generator-v2:0";

    private final ObjectMapper objectMapper;

    public ImageGenerationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Image generateImageByDescription(@NonNull String description) {
        String jsonRequest = createRequest(description);

        try (BedrockRuntimeClient client = createClient()) {

            var response = client.invokeModel(request -> request.body(SdkBytes.fromUtf8String(jsonRequest))
                    .modelId(IMAGE_GEN_MODEL)
                    .accept(BedrockImageParser.IMAGE_MEDIA_TYPE));

            return new BedrockImageParser(objectMapper).parseResponse(description, response.body().asByteArray());
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
}
