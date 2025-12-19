package com.example.chicken.common.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3UrlProvider {

    private final String baseUrl;

    public S3UrlProvider(@Value("${s3.product-bucket-url}") String rawBaseUrl) {
        this.baseUrl = rawBaseUrl.endsWith("/") ? rawBaseUrl : rawBaseUrl + "/";
    }

    public String generateUrl(String filename) {
        if (filename == null || filename.isBlank()) {
            return null;
        }

        String cleanedFileName = filename.startsWith("/") ? filename.substring(1) : filename;
        return baseUrl + cleanedFileName;
    }

    public List<String> generateUrls(List<String> filenames) {
        if (filenames == null) {
            return Collections.emptyList();
        }

        return filenames.stream()
                .map(this::generateUrl)
                .toList();
    }

}
