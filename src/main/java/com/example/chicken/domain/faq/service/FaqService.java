package com.example.chicken.domain.faq.service;

import com.example.chicken.domain.faq.dto.FaqRequestDto;
import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.entity.Faq;
import com.example.chicken.domain.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaqService {

    private final FaqRepository faqRepository;

    /**
     * FAQ 생성
     *
     * @param faqRequestDto FAQ 생성 요청 DTO
     */
    @Transactional
    public void createFaq(FaqRequestDto faqRequestDto) {
        Faq faq = Faq.builder()
            .title(faqRequestDto.title())
            .content(faqRequestDto.content())
            .build();
        faqRepository.save(faq);
    }

    /**
     * FAQ 목록 조회
     *
     * @return FAQ 목록 응답 DTO 리스트
     */
    public List<FaqResponseDto> getFaqs() {
        return faqRepository.findAll().stream()
            .map(faq -> FaqResponseDto.builder()
                .faqId(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .createdBy(faq.getCreatedBy())
                .createdAt(faq.getCreatedAt())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * FAQ 수정
     *
     * @param faqId         수정할 FAQ ID
     * @param faqRequestDto FAQ 수정 요청 DTO
     * @return 수정된 FAQ 응답 DTO
     */
    @Transactional
    public FaqResponseDto updateFaq(Long faqId, FaqRequestDto faqRequestDto) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new IllegalArgumentException("해당 FAQ를 찾을 수 없습니다."));
        faq.updateFaq(faqRequestDto);
        return FaqResponseDto.builder()
            .faqId(faq.getFaqId())
            .title(faq.getTitle())
            .content(faq.getContent())
            .createdBy(faq.getCreatedBy())
            .createdAt(faq.getCreatedAt())
            .build();
    }

    /**
     * FAQ 삭제
     *
     * @param faqId 삭제할 FAQ ID
     */
    @Transactional
    public void deleteFaq(Long faqId) {
        faqRepository.deleteById(faqId);
    }
}
