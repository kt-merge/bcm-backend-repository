package com.example.chicken.domain.faq.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record FaqRequestDto(
    @NotBlank(message = "제목을 입력해주세요.")
    String title,
    @NotBlank(message = "내용을 입력해주세요.")
    String content
) {}
