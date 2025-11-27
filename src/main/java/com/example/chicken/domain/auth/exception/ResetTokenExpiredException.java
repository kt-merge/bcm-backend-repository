package com.example.chicken.domain.auth.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class ResetTokenExpiredException extends BusinessException {
	public ResetTokenExpiredException() {
		super(ErrorCode.EXPIRED_TOKEN);
	}
}
