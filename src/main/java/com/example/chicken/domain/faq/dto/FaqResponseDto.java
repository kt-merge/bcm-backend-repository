package com.example.chicken.domain.faq.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FaqResponseDto(
    Long faqId,
    String title,
    String content,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {}
