package com.example.chicken.domain.qna.service;

import com.example.chicken.domain.qna.dto.QnaRequestDto;
import com.example.chicken.domain.qna.dto.QnaResponseDto;
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

    /**
     * QNA 생성
     *
     * @param qnaRequestDto QNA 생성 요청 DTO
     * @return 생성된 QNA 응답 DTO
     */
    @Transactional
    public QnaResponseDto createQna(QnaRequestDto qnaRequestDto) {
        Qna qna = qnaMapper.toEntity(qnaRequestDto);
        Qna savedQna = qnaRepository.save(qna);
        return qnaMapper.toDto(savedQna);
    }

    /**
     * QNA 목록 조회
     *
     * @param pageable 페이징 정보
     * @return QNA 페이지 응답 DTO
     */
    @Transactional(readOnly = true)
    public Page<QnaResponseDto> getQnas(Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        return qnas.map(qnaMapper::toDto);
    }

    /**
     * QNA 수정
     *
     * @param qnaId         수정할 QNA ID
     * @param qnaRequestDto QNA 수정 요청 DTO
     * @return 수정된 QNA 응답 DTO
     */
    @Transactional
    public QnaResponseDto updateQna(Long qnaId, QnaRequestDto qnaRequestDto) {
        Qna qna = qnaRepository.findById(qnaId)
            .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qna.updateQna(qnaRequestDto);
        return qnaMapper.toDto(qna);
    }

    /**
     * QNA 삭제
     *
     * @param qnaId 삭제할 QNA ID
     */
    @Transactional
    public void deleteQna(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
            .orElseThrow(() -> new QnaNotFoundException("해당 QNA를 찾을 수 없습니다. id=" + qnaId));
        qnaRepository.delete(qna);
    }
}
