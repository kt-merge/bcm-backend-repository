package com.example.chicken.domain.faq.entity;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.faq.dto.FaqRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Faq extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;

    private String title;
    private String content;

    @CreatedBy
    private String createdBy;

    @Builder
    public Faq(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateFaq(FaqRequestDto faqRequestDto) {
        this.title = faqRequestDto.title();
        this.content = faqRequestDto.content();
    }
}
