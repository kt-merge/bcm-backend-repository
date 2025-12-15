package com.example.chicken.domain.qna.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class QnaNotFoundException extends EntityNotFoundException {
    public QnaNotFoundException(String message) {
        super(message);
    }
}
