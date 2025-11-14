package com.example.chicken.common.service;

import java.time.Duration;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3Provider {

	@Value("${s3.bucket-name}")
	private String bucketName;
	private final S3Presigner s3Presigner;

	public String generateUploadUrl(String key) {
		String pathKey = "products/" + key;

		PutObjectRequest objectRequest = PutObjectRequest.builder()
			.bucket(this.bucketName)
			.key(pathKey)
			.contentType(extractExtension(key))
			.build();

		PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
			.putObjectRequest(objectRequest)
			.signatureDuration(Duration.ofMinutes(5))
			.build();

		PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignRequest);

		return presigned.url().toString();
	}

	public String generateDownloadUrl(String key) {
		GetObjectRequest getOBjectRequest = GetObjectRequest.builder()
			.bucket(this.bucketName)
			.key(key)
			.build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
			.getObjectRequest(getOBjectRequest)
			.signatureDuration(Duration.ofMinutes(5))
			.build();

		PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);

		return presigned.url().toString();
	}

	private String extractExtension(String key) {
		String extension = key.substring(key.lastIndexOf(".") + 1).toLowerCase();

		return switch (extension) {
			case "png" -> ContentType.IMAGE_PNG.getMimeType();
			case "jpg", "jpge" -> ContentType.IMAGE_JPEG.getMimeType();
			case "gif" -> ContentType.IMAGE_GIF.getMimeType();
			case "webp" -> ContentType.IMAGE_WEBP.getMimeType();
			default -> ContentType.APPLICATION_OCTET_STREAM.getMimeType();
		};
	}

}
