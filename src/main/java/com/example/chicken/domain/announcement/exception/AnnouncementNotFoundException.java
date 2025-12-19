package com.example.chicken.domain.announcement.exception;

import com.example.chicken.common.error.exception.EntityNotFoundException;

public class AnnouncementNotFoundException extends EntityNotFoundException {
    public AnnouncementNotFoundException(String message) {
        super(message);
    }
}
