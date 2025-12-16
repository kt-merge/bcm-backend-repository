package com.example.chicken.domain.qna.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record QnaResponseDto(
    Long qnaId,
    String question,
    String answer,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {}
