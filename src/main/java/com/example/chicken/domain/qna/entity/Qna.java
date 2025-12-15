package com.example.chicken.domain.qna.entity;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.qna.dto.QnaRequestDto;
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
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaId;

    private String question;
    private String answer;

    @CreatedBy
    private String createdBy;

    @Builder
    public Qna(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void updateQna(QnaRequestDto qnaRequestDto) {
        this.question = qnaRequestDto.question();
        this.answer = qnaRequestDto.answer();
    }
}
