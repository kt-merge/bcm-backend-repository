package com.example.chicken.domain.announcement.dto;

import com.example.chicken.domain.announcement.entity.AnnouncementCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnnouncementRequestDto(
    @NotBlank(message = "제목을 입력해주세요.")
    String title,
    @NotBlank(message = "내용을 입력해주세요.")
    String content,
    @NotNull(message = "구분을 선택해주세요.")
    AnnouncementCategory category
) {
}
