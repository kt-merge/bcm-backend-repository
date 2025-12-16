package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_FAQ_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_FAQ_PREFIX;

import com.example.chicken.domain.faq.dto.FaqRequestDto;
import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.service.FaqService;
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
@RequestMapping(ADMIN_FAQ_PREFIX)
public class AdminFaqController {

    private final FaqService faqService;

    @PostMapping
    public ResponseEntity<FaqResponseDto> createFaq(@Valid @RequestBody FaqRequestDto faqRequestDto) {
        log.info("FAQ 생성 요청. 내용: {}", faqRequestDto);
        FaqResponseDto createdFaq = faqService.createFaq(faqRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaq);
    }

    @GetMapping
    public ResponseEntity<Page<FaqResponseDto>> getFaqs(Pageable pageable) {
        log.info("FAQ 목록 조회 요청");
        return ResponseEntity.ok(faqService.getFaqs(pageable));
    }

    @PutMapping(ADMIN_FAQ_ID)
    public ResponseEntity<FaqResponseDto> updateFaq(@PathVariable Long faqId,
        @Valid @RequestBody FaqRequestDto faqRequestDto) {
        log.info("FAQ 수정 요청. ID: {}, 내용: {}", faqId, faqRequestDto);
        return ResponseEntity.ok(faqService.updateFaq(faqId, faqRequestDto));
    }

    @DeleteMapping(ADMIN_FAQ_ID)
    public ResponseEntity<Void> deleteFaq(@PathVariable Long faqId) {
        log.info("FAQ 삭제 요청. ID: {}", faqId);
        faqService.deleteFaq(faqId);
        return ResponseEntity.noContent().build();
    }
}
