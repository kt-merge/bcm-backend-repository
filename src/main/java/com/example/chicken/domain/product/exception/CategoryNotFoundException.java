package com.example.chicken.domain.product.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(String target) {
        super("카테고리를 찾을 수 없습니다: " + target);
    }
}
