package com.example.chicken.domain.announcement.repository;

import com.example.chicken.domain.announcement.dto.AnnouncementResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementRepositoryCustom {
    Page<AnnouncementResponseDto> searchAnnouncements(Pageable pageable);
}
