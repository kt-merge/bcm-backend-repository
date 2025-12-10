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

@Entity // JPA 엔티티 클래스임을 선언 (DB 테이블로 매핑됨)
@Getter // 모든 필드에 대한 Getter 메서드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시 생성을 위해 기본 생성자 필요, 외부 생성은 막음
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화 (@CreatedDate, @CreatedBy 자동 적용)
public class Faq {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment 방식 자동 생성
    private Long faqId;

    private String title;
    private String content;

    @CreatedBy // 엔티티 생성 시 생성자(작성자) 자동 기록
    private String createdBy;

    @CreatedDate // 엔티티 생성 시 시간 자동 기록
    private LocalDateTime createdAt;

    @Builder // 빌더 패턴 생성자 제공
    public Faq(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // FAQ 데이터 수정 로직
    public void updateFaq(FaqRequestDto faqRequestDto) {
        this.title = faqRequestDto.title();     // title 갱신
        this.content = faqRequestDto.content(); // content 갱신
    }
}
