package com.example.chicken.domain.product.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class CategoryAlreadyExists extends BusinessException {

    public CategoryAlreadyExists() {
        super(ErrorCode.CATEGORY_ALREADY_EXISTS);
    }

}
