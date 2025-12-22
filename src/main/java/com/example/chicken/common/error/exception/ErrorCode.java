package com.example.chicken.common.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_ALREADY_EXISTS("AUTH001", HttpStatus.CONFLICT.value(), "이미 존재하는 사용자입니다."),
    PASSWORD_NOT_MATCHED("AUTH002", HttpStatus.UNAUTHORIZED.value(), "비밀번호가 일치하지 않습니다."),
    TOKEN_INVALID("AUTH003", HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("AUTH004", HttpStatus.GONE.value(), "만료된 토큰입니다."),
    WHY_DELETE_ME("AUTH005", HttpStatus.FORBIDDEN.value(), "당신은 당신을 지울 수 없습니다."),
    ROLE_NOT_ALLOWED("AUTH006", HttpStatus.FORBIDDEN.value(), "허용되지 않은 역할입니다."),

    ENTITY_NOT_FOUND("COMM001", HttpStatus.NOT_FOUND.value(), "엔티티를 찾을 수 없습니다."),

    CATEGORY_ALREADY_EXISTS("CATE001", HttpStatus.CONFLICT.value(), "이미 존재하는 카테고리입니다."),
    EXISTS_CATEGORY_IN_PRODUCT("CATE002", HttpStatus.CONFLICT.value(), "카테고리에 속한 상품이 있어 삭제할 수 없습니다."),

    PAYMENT_AMOUNT_MISMATCH_EXCEPTION("PAYM001", HttpStatus.BAD_REQUEST.value(), "결제 금액이 일치하지 않습니다."),
    PAYMENT_ALREADY_PAID_EXCEPTION("PAYM002", HttpStatus.BAD_REQUEST.value(), "이미 결제 완료된 주문입니다."),
    PAYMENT_CONFIRM_FAILED("PAYM003", HttpStatus.BAD_REQUEST.value(), "결제 승인에 실패했습니다."),
    PAYMENT_REQUEST_SERIALIZATION_FAILED("PAYM004", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "결제 요청 데이터 처리 중 오류가 발생했습니다."),
    PAYMENT_API_CONNECTION_FAILED("PAYM005", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "외부 결제 시스템 연동 중 오류가 발생했습니다.");

    private final String code;
    private final int status;
    private final String message;
}
