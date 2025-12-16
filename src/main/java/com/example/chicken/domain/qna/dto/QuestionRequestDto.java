package com.example.chicken.domain.qna.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record QuestionRequestDto(
    @NotBlank(message = "질문을 입력해주세요.")
    String question
) {}
