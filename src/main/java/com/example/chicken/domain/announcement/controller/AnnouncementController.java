package com.example.chicken.domain.announcement.controller;

import static com.example.chicken.common.constant.PathConstant.Announcement.ANNOUNCEMENT_ID;
import static com.example.chicken.common.constant.PathConstant.Announcement.ANNOUNCEMENT_PREFIX;

import com.example.chicken.domain.announcement.dto.AnnouncementResponseDto;
import com.example.chicken.domain.announcement.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ANNOUNCEMENT_PREFIX)
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<Page<AnnouncementResponseDto>> getAnnouncements(Pageable pageable) {
        log.info("공지사항 목록 조회 요청");
        return ResponseEntity.ok(announcementService.getAnnouncements(pageable));
    }

    @GetMapping(ANNOUNCEMENT_ID)
    public ResponseEntity<AnnouncementResponseDto> getAnnouncement(@PathVariable Long announcementId) {
        log.info("공지사항 단일 조회 요청. ID: {}", announcementId);
        return ResponseEntity.ok(announcementService.getAnnouncement(announcementId));
    }
}
