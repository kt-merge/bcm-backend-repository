package com.example.chicken.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AnswerRequestDto(
    @NotBlank(message = "답변을 입력해주세요.")
    String answer
) {}
