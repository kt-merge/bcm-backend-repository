package com.example.chicken.domain.faq.service;

import com.example.chicken.domain.faq.dto.FaqRequestDto;
import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.entity.Faq;
import com.example.chicken.domain.faq.exception.FaqNotFoundException;
import com.example.chicken.domain.faq.mapper.FaqMapper;
import com.example.chicken.domain.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final FaqMapper faqMapper;

    @Transactional
    public FaqResponseDto createFaq(FaqRequestDto faqRequestDto) {
        Faq faq = faqMapper.toEntity(faqRequestDto);
        Faq savedFaq = faqRepository.save(faq);
        return faqMapper.toDto(savedFaq);
    }

    @Transactional(readOnly = true)
    public Page<FaqResponseDto> getFaqs(Pageable pageable) {
        Page<Faq> faqs = faqRepository.findAll(pageable);
        return faqs.map(faqMapper::toDto);
    }

    @Transactional
    public FaqResponseDto updateFaq(Long faqId, FaqRequestDto faqRequestDto) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new FaqNotFoundException("해당 FAQ를 찾을 수 없습니다. id=" + faqId));
        faq.updateFaq(faqRequestDto);
        return faqMapper.toDto(faq);
    }

    @Transactional
    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
            .orElseThrow(() -> new FaqNotFoundException("해당 FAQ를 찾을 수 없습니다. id=" + faqId));
        faqRepository.delete(faq);
    }
}
