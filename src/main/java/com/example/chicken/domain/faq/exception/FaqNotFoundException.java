package com.example.chicken.domain.faq.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class FaqNotFoundException extends EntityNotFoundException {
    public FaqNotFoundException(String message) {
        super(message);
    }
}
