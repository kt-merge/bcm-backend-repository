package com.example.chicken.domain.faq.controller;

import static com.example.chicken.common.constant.PathConstant.Faq.FAQ_PREFIX;

import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.service.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(FAQ_PREFIX)
public class FaqController {

    private final FaqService faqService;

    @GetMapping
    public ResponseEntity<Page<FaqResponseDto>> getFaqs(Pageable pageable) {
        log.info("FAQ 목록 조회 요청");
        return ResponseEntity.ok(faqService.getFaqs(pageable));
    }
}
