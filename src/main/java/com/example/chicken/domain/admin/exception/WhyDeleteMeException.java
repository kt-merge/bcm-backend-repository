package com.example.chicken.domain.admin.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class WhyDeleteMeException extends BusinessException {
    public WhyDeleteMeException() {
        super(ErrorCode.WHY_DELETE_ME);
    }
}
