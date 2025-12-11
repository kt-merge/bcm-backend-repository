package com.example.chicken.domain.faq.entity;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화 (@CreatedDate, @CreatedBy 자동 적용)
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;

    private String title;
    private String content;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

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
