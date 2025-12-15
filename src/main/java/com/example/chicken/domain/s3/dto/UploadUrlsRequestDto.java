package com.example.chicken.domain.s3.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UploadUrlsRequestDto(
        @NotNull
        List<String> uploadUrls) {
}
