package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_ANNOUNCEMENT_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_ANNOUNCEMENT_PREFIX;

import com.example.chicken.domain.announcement.dto.AnnouncementRequestDto;
import com.example.chicken.domain.announcement.dto.AnnouncementResponseDto;
import com.example.chicken.domain.announcement.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_ANNOUNCEMENT_PREFIX)
public class AdminAnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<AnnouncementResponseDto> createAnnouncement(@Valid @RequestBody AnnouncementRequestDto requestDto) {
        log.info("관리자: 공지사항 생성 요청. 제목: {}, 내용: {}, 구분: {}", requestDto.title(), requestDto.content(), requestDto.category());
        AnnouncementResponseDto createdAnnouncement = announcementService.createAnnouncement(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnouncement);
    }

    @GetMapping
    public ResponseEntity<Page<AnnouncementResponseDto>> getAnnouncements(Pageable pageable) {
        log.info("관리자: 공지사항 목록 조회 요청");
        return ResponseEntity.ok(announcementService.getAnnouncements(pageable));
    }

    @GetMapping(ADMIN_ANNOUNCEMENT_ID)
    public ResponseEntity<AnnouncementResponseDto> getAnnouncement(@PathVariable Long announcementId) {
        log.info("관리자: 공지사항 단일 조회 요청. ID: {}", announcementId);
        return ResponseEntity.ok(announcementService.getAnnouncement(announcementId));
    }

    @PutMapping(ADMIN_ANNOUNCEMENT_ID)
    public ResponseEntity<AnnouncementResponseDto> updateAnnouncement(@PathVariable Long announcementId,
        @Valid @RequestBody AnnouncementRequestDto requestDto) {
        log.info("관리자: 공지사항 수정 요청. ID: {}, 제목: {}, 내용: {}, 구분: {}", announcementId, requestDto.title(), requestDto.content(), requestDto.category());
        return ResponseEntity.ok(announcementService.updateAnnouncement(announcementId, requestDto));
    }

    @DeleteMapping(ADMIN_ANNOUNCEMENT_ID)
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId) {
        log.info("관리자: 공지사항 삭제 요청. ID: {}", announcementId);
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.noContent().build();
    }
}
