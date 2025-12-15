package com.example.chicken.domain.s3.dto;

import java.util.List;

public record UploadUrlsRequestDto(List<String> uploadUrls) {
}
