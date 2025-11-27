package com.example.chicken.domain.auth.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class TokenInvalidException extends BusinessException {
	public TokenInvalidException() {
		super(ErrorCode.TOKEN_INVALID);
	}
}
