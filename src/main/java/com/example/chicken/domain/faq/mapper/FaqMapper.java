package com.example.chicken.domain.faq.mapper;

import com.example.chicken.domain.faq.dto.FaqRequestDto;
import com.example.chicken.domain.faq.dto.FaqResponseDto;
import com.example.chicken.domain.faq.entity.Faq;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FaqMapper {

    public Faq toEntity(FaqRequestDto dto) {
        return Faq.builder()
                .title(dto.title())
                .content(dto.content())
                .build();
    }

    public FaqResponseDto toDto(Faq faq) {
        return FaqResponseDto.builder()
                .faqId(faq.getFaqId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .createdBy(faq.getCreatedBy())
                .createdAt(faq.getCreatedAt())
                .build();
    }

    public List<FaqResponseDto> toDtoList(List<Faq> faqs) {
        return faqs.stream()
                .map(this::toDto)
                .toList();
    }
}
