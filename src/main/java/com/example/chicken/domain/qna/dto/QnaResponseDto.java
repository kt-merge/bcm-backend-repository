package com.example.chicken.domain.qna.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QnaResponseDto(
    Long qnaId,
    String question,
    String answer,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {}
