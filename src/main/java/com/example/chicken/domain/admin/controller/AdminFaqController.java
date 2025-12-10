package com.example.chicken.domain.admin.controller;

import com.example.chicken.domain.faq.dto.FaqRequestDto;
import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.service.FaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

/**
 * FAQ 관련 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/faq")
public class AdminFaqController {

    private final FaqService faqService;

    /**
     * FAQ 생성
     *
     * @param faqRequestDto FAQ 생성 요청 DTO
     * @return 생성 성공 시 201 Created
     */
    @PostMapping
    public ResponseEntity<Void> createFaq(@Valid @RequestBody FaqRequestDto faqRequestDto) {
        faqService.createFaq(faqRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * FAQ 목록 조회
     *
     * @return FAQ 목록
     */
    @GetMapping
    public ResponseEntity<List<FaqResponseDto>> getFaqs() {
        return ResponseEntity.ok(faqService.getFaqs());
    }

    /**
     * FAQ 수정
     *
     * @param faqId         수정할 FAQ ID
     * @param faqRequestDto FAQ 수정 요청 DTO
     * @return 수정된 FAQ
     */
    @PutMapping("/{faqId}")
    public ResponseEntity<FaqResponseDto> updateFaq(@PathVariable Long faqId, @Valid @RequestBody FaqRequestDto faqRequestDto) {
        return ResponseEntity.ok(faqService.updateFaq(faqId, faqRequestDto));
    }

    /**
     * FAQ 삭제
     *
     * @param faqId 삭제할 FAQ ID
     * @return 삭제 성공 시 204 No Content
     */
    @DeleteMapping("/{faqId}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return ResponseEntity.noContent().build();
    }
}
