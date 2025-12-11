package com.example.chicken.domain.admin.dto;

import java.time.LocalDate;

public record DailyUserRegistrationCountDto(LocalDate date, Long count) {
}
