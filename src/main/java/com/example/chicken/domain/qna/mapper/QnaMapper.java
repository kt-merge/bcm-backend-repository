package com.example.chicken.domain.qna.mapper;

import com.example.chicken.domain.qna.dto.QuestionRequestDto;
import com.example.chicken.domain.qna.dto.QnaResponseDto;
import com.example.chicken.domain.qna.entity.Qna;
import org.springframework.stereotype.Component;

@Component
public class QnaMapper {

    public Qna toEntity(QuestionRequestDto dto) {
        return Qna.builder()
                .question(dto.question())
                .answer(null)
                .build();
    }

    public QnaResponseDto toDto(Qna qna) {
        return QnaResponseDto.builder()
                .qnaId(qna.getQnaId())
                .question(qna.getQuestion())
                .answer(qna.getAnswer())
                .createdBy(qna.getCreatedBy())
                .createdAt(qna.getCreatedAt())
                .modifiedAt(qna.getModifiedAt())
                .build();
    }
}
