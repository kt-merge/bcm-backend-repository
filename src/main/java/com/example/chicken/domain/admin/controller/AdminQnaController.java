package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_QNA_ANSWER;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_QNA_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_QNA_PREFIX;

import com.example.chicken.domain.qna.dto.AnswerRequestDto;
import com.example.chicken.domain.qna.dto.QnaResponseDto;
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
@RequestMapping(ADMIN_QNA_PREFIX)
public class AdminQnaController {

    private final QnaService qnaService;

    @PostMapping(ADMIN_QNA_ANSWER)
    public ResponseEntity<QnaResponseDto> createAnswer(@PathVariable Long qnaId, @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        log.info("답변 생성 요청. QNA ID: {}, 내용: {}", qnaId, answerRequestDto);
        QnaResponseDto createdAnswer = qnaService.createAnswer(qnaId, answerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    @GetMapping
    public ResponseEntity<Page<QnaResponseDto>> getQnas(Pageable pageable) {
        log.info("QNA 목록 조회 요청");
        return ResponseEntity.ok(qnaService.getQnas(pageable));
    }

    @GetMapping(ADMIN_QNA_ID)
    public ResponseEntity<QnaResponseDto> getQna(@PathVariable Long qnaId) {
        log.info("QNA 단일 조회 요청. ID: {}", qnaId);
        return ResponseEntity.ok(qnaService.getQna(qnaId));
    }

    @PutMapping(ADMIN_QNA_ANSWER)
    public ResponseEntity<QnaResponseDto> updateAnswer(@PathVariable Long qnaId,
        @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        log.info("답변 수정 요청. QNA ID: {}, 내용: {}", qnaId, answerRequestDto);
        return ResponseEntity.ok(qnaService.updateAnswer(qnaId, answerRequestDto));
    }

    @DeleteMapping(ADMIN_QNA_ID)
    public ResponseEntity<Void> deleteQna(@PathVariable Long qnaId) {
        log.info("QNA 삭제 요청. ID: {}", qnaId);
        qnaService.deleteQna(qnaId);
        return ResponseEntity.noContent().build();
    }
}
