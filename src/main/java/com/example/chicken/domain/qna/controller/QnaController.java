package com.example.chicken.domain.qna.controller;

import static com.example.chicken.common.constant.PathConstant.Qna.QNA_ID;
import static com.example.chicken.common.constant.PathConstant.Qna.QNA_PREFIX;

import com.example.chicken.domain.qna.dto.QnaResponseDto;
import com.example.chicken.domain.qna.dto.QuestionRequestDto;
import com.example.chicken.domain.qna.service.QnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(QNA_PREFIX)
public class QnaController {

    private final QnaService qnaService;

    @PostMapping
    public ResponseEntity<QnaResponseDto> createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto) {
        log.info("질문 생성 요청. 내용: {}", questionRequestDto);
        QnaResponseDto createdQna = qnaService.createQuestion(questionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQna);
    }

    @GetMapping
    public ResponseEntity<Page<QnaResponseDto>> getQnas(Pageable pageable) {
        log.info("QNA 목록 조회 요청");
        return ResponseEntity.ok(qnaService.getQnas(pageable));
    }

    @GetMapping(QNA_ID)
    public ResponseEntity<QnaResponseDto> getQna(@PathVariable Long qnaId) {
        log.info("QNA 단일 조회 요청. ID: {}", qnaId);
        return ResponseEntity.ok(qnaService.getQna(qnaId));
    }

    @PutMapping(QNA_ID)
    public ResponseEntity<QnaResponseDto> updateQuestion(@PathVariable Long qnaId,
                                                    @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        log.info("질문 수정 요청. ID: {}, 내용: {}", qnaId, questionRequestDto);
        return ResponseEntity.ok(qnaService.updateQuestion(qnaId, questionRequestDto));
    }

    @DeleteMapping(QNA_ID)
    public ResponseEntity<Void> deleteQna(@PathVariable Long qnaId) {
        log.info("QNA 삭제 요청. ID: {}", qnaId);
        qnaService.deleteQna(qnaId);
        return ResponseEntity.noContent().build();
    }
}
