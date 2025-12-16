package com.example.chicken.domain.qna.service;

import com.example.chicken.domain.qna.dto.AnswerRequestDto;
import com.example.chicken.domain.qna.dto.QnaResponseDto;
import com.example.chicken.domain.qna.dto.QuestionRequestDto;
import com.example.chicken.domain.qna.entity.Qna;
import com.example.chicken.domain.qna.exception.QnaNotFoundException;
import com.example.chicken.domain.qna.mapper.QnaMapper;
import com.example.chicken.domain.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final QnaMapper qnaMapper;

    // 사용자용 (질문)
    @Transactional
    public QnaResponseDto createQuestion(QuestionRequestDto questionRequestDto) {
        Qna qna = qnaMapper.toEntity(questionRequestDto);
        Qna savedQna = qnaRepository.save(qna);
        return qnaMapper.toDto(savedQna);
    }

    @Transactional
    public QnaResponseDto updateQuestion(Long qnaId, QuestionRequestDto questionRequestDto) {
        Qna qna = qnaRepository.findById(qnaId)
            .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qna.updateQuestion(questionRequestDto);
        return qnaMapper.toDto(qna);
    }

    // 관리자용 (답변)
    @Transactional
    public QnaResponseDto createAnswer(Long qnaId, AnswerRequestDto answerRequestDto) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qna.updateAnswer(answerRequestDto);
        return qnaMapper.toDto(qna);
    }

    @Transactional
    public QnaResponseDto updateAnswer(Long qnaId, AnswerRequestDto answerRequestDto) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qna.updateAnswer(answerRequestDto);
        return qnaMapper.toDto(qna);
    }

    // 공용 (조회, 삭제)
    @Transactional(readOnly = true)
    public QnaResponseDto getQna(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
            .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        return qnaMapper.toDto(qna);
    }

    @Transactional(readOnly = true)
    public Page<QnaResponseDto> getQnas(Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        return qnas.map(qnaMapper::toDto);
    }

    @Transactional
    public void deleteQna(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
            .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qnaRepository.delete(qna);
    }
}
