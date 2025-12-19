package com.example.chicken.domain.announcement.entity;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.announcement.dto.AnnouncementRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;

    private String title;
    private String content;
    private String category;

    @CreatedBy
    private String createdBy;

    @Builder
    public Announcement(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void updateAnnouncement(AnnouncementRequestDto announcementRequestDto) {
        this.title = announcementRequestDto.title();
        this.content = announcementRequestDto.content();
        this.category = announcementRequestDto.category();
    }
}
