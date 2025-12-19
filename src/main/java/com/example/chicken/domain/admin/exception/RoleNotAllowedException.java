package com.example.chicken.domain.admin.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class RoleNotAllowedException extends BusinessException {
    public RoleNotAllowedException() {
        super(ErrorCode.ROLE_NOT_ALLOWED);
    }
}
