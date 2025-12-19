package com.example.chicken.domain.announcement.service;

import com.example.chicken.domain.announcement.dto.AnnouncementRequestDto;
import com.example.chicken.domain.announcement.dto.AnnouncementResponseDto;
import com.example.chicken.domain.announcement.entity.Announcement;
import com.example.chicken.domain.announcement.exception.AnnouncementNotFoundException;
import com.example.chicken.domain.announcement.mapper.AnnouncementMapper;
import com.example.chicken.domain.announcement.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;

    @Transactional
    public AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto requestDto) {
        Announcement announcement = announcementMapper.toEntity(requestDto);
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return announcementMapper.toDto(savedAnnouncement);
    }

    @Transactional(readOnly = true)
    public Page<AnnouncementResponseDto> getAnnouncements(Pageable pageable) {
        return announcementRepository.findAll(pageable).map(announcementMapper::toDto);
    }

    @Transactional(readOnly = true)
    public AnnouncementResponseDto getAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
            .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found with id: " + announcementId));
        return announcementMapper.toDto(announcement);
    }

    @Transactional
    public AnnouncementResponseDto updateAnnouncement(Long announcementId, AnnouncementRequestDto requestDto) {
        Announcement announcement = announcementRepository.findById(announcementId)
            .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found with id: " + announcementId));
        announcement.updateAnnouncement(requestDto);
        return announcementMapper.toDto(announcement);
    }

    @Transactional
    public void deleteAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
            .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found with id: " + announcementId));
        announcementRepository.delete(announcement);
    }
}
