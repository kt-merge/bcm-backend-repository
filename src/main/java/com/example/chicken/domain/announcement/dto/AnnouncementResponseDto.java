package com.example.chicken.domain.announcement.dto;


import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record AnnouncementResponseDto(
    Long announcementId,
    String title,
    String content,
    String category,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {}
