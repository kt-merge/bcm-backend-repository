package com.example.chicken.domain.s3.service;

import com.example.chicken.domain.s3.dto.UploadUrlsRequestDto;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3Provider {

    @Value("${s3.bucket-name}")
    private String bucketName;

    private static final String PRODUCT_DIR = "products/";
    private final S3Presigner s3Presigner;

    public List<String> generateUploadUrls(UploadUrlsRequestDto keys) {

        return keys.uploadUrls().stream()
                .map(this::generateSingleUploadUrl)
                .collect((Collectors.toList()));
    }

    private String generateSingleUploadUrl(String key) {
        String pathKey = PRODUCT_DIR + key;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(this.bucketName)
                .key(pathKey)
                .contentType(extractExtension(key))
                .build();

        PutObjectPresignRequest presignedRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(objectRequest)
                .signatureDuration(Duration.ofMinutes(5))
                .build();

        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignedRequest);

        return presigned.url().toString();
    }

    private String extractExtension(String key) {
        String extension = key.substring(key.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "png" -> ContentType.IMAGE_PNG.getMimeType();
            case "jpg", "jpeg" -> ContentType.IMAGE_JPEG.getMimeType();
            case "gif" -> ContentType.IMAGE_GIF.getMimeType();
            case "webp" -> ContentType.IMAGE_WEBP.getMimeType();
            default -> ContentType.APPLICATION_OCTET_STREAM.getMimeType();
        };
    }

}