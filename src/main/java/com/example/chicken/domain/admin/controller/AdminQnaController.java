package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_QNA_ID;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_QNA_PREFIX;

import com.example.chicken.domain.qna.dto.QnaRequestDto;
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

    /**
     * QNA 생성
     *
     * @param qnaRequestDto QNA 생성 요청 DTO
     * @return 생성된 QNA 정보
     */
    @PostMapping
    public ResponseEntity<QnaResponseDto> createQna(@Valid @RequestBody QnaRequestDto qnaRequestDto) {
        log.info("QNA 생성 요청. 내용: {}", qnaRequestDto);
        QnaResponseDto createdQna = qnaService.createQna(qnaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQna);
    }

    /**
     * QNA 목록 조회
     *
     * @param pageable 페이징 정보
     * @return QNA 목록
     */
    @GetMapping
    public ResponseEntity<Page<QnaResponseDto>> getQnas(Pageable pageable) {
        log.info("QNA 목록 조회 요청");
        return ResponseEntity.ok(qnaService.getQnas(pageable));
    }

    /**
     * QNA 수정
     *
     * @param qnaId         수정할 QNA ID
     * @param qnaRequestDto QNA 수정 요청 DTO
     * @return 수정된 QNA
     */
    @PutMapping(ADMIN_QNA_ID)
    public ResponseEntity<QnaResponseDto> updateQna(@PathVariable Long qnaId,
        @Valid @RequestBody QnaRequestDto qnaRequestDto) {
        log.info("QNA 수정 요청. ID: {}, 내용: {}", qnaId, qnaRequestDto);
        return ResponseEntity.ok(qnaService.updateQna(qnaId, qnaRequestDto));
    }

    /**
     * QNA 삭제
     *
     * @param qnaId 삭제할 QNA ID
     * @return 삭제 성공 시 204 No Content
     */
    @DeleteMapping(ADMIN_QNA_ID)
    public ResponseEntity<Void> deleteQna(@PathVariable Long qnaId) {
        log.info("QNA 삭제 요청. ID: {}", qnaId);
        qnaService.deleteQna(qnaId);
        return ResponseEntity.noContent().build();
    }
}
