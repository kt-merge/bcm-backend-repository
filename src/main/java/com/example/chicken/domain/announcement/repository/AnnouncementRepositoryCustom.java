package com.example.chicken.domain.announcement.repository;

import com.example.chicken.domain.announcement.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementRepositoryCustom {
    Page<Announcement> searchAnnouncements(String keyword, Pageable pageable);
}
