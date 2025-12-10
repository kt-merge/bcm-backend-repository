package com.example.chicken.domain.product.exception;

import com.example.chicken.common.error.exception.BusinessException;
import com.example.chicken.common.error.exception.ErrorCode;

public class ExistsCategoryInProduct extends BusinessException {
    public ExistsCategoryInProduct() {
        super(ErrorCode.EXISTS_CATEGORY_IN_PRODUCT);
    }
}
