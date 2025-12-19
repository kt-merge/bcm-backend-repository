package com.example.chicken.domain.announcement.mapper;

import com.example.chicken.domain.announcement.dto.AnnouncementRequestDto;
import com.example.chicken.domain.announcement.dto.AnnouncementResponseDto;
import com.example.chicken.domain.announcement.entity.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {

    public Announcement toEntity(AnnouncementRequestDto dto) {
        return Announcement.builder()
                .title(dto.title())
                .content(dto.content())
                .category(dto.category())
                .build();
    }

    public AnnouncementResponseDto toDto(Announcement announcement) {
        return AnnouncementResponseDto.builder()
                .announcementId(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .category(announcement.getCategory())
                .createdBy(announcement.getCreatedBy())
                .createdAt(announcement.getCreatedAt())
                .modifiedAt(announcement.getModifiedAt())
                .build();
    }
}
