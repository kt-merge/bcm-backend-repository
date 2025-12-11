package com.example.chicken.domain.faq.service;

import com.example.chicken.domain.faq.dto.FaqRequestDto;
import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.entity.Faq;
import com.example.chicken.domain.faq.mapper.FaqMapper;
import com.example.chicken.domain.faq.repository.FaqRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final FaqMapper faqMapper;

    /**
     * FAQ 생성
     *
     * @param faqRequestDto FAQ 생성 요청 DTO
     * @return 생성된 FAQ 응답 DTO
     */
    @Transactional
    public FaqResponseDto createFaq(FaqRequestDto faqRequestDto) {
        Faq faq = faqMapper.toEntity(faqRequestDto);
        Faq savedFaq = faqRepository.save(faq);
        return faqMapper.toDto(savedFaq);
    }

    /**
     * FAQ 목록 조회
     *
     * @return FAQ 목록 응답 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<FaqResponseDto> getFaqs() {
        List<Faq> faqs = faqRepository.findAll();
        return faqMapper.toDtoList(faqs);
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
        return faqMapper.toDto(faq);
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
