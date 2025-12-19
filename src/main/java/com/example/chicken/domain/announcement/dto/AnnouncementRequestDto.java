package com.example.chicken.domain.announcement.dto;

import jakarta.validation.constraints.NotBlank;

public record AnnouncementRequestDto(
    @NotBlank(message = "제목을 입력해주세요.")
    String title,
    @NotBlank(message = "내용을 입력해주세요.")
    String content,
    @NotBlank(message = "구분을 입력해주세요. (예: 공지, 안내)")
    String category
) {
}
