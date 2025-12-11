package com.example.chicken.domain.auth.dto.user;

import java.time.LocalDate;

public record DailyUserRegistrationCountDto(LocalDate date, Long count) {
}
