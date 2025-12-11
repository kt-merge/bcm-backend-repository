package com.example.chicken.domain.admin.dto;

import java.time.LocalDate;

public record DailyProductRegistrationCountDto(LocalDate date, Long count) {
}
