package com.example.chicken.domain.announcement.dto;


import com.example.chicken.domain.announcement.entity.AnnouncementCategory;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record AnnouncementResponseDto(
    Long announcementId,
    String title,
    String content,
    AnnouncementCategory category,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {}
