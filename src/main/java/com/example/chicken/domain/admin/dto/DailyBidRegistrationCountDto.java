package com.example.chicken.domain.admin.dto;

import java.time.LocalDate;

public record DailyBidRegistrationCountDto(LocalDate date, Long count) {
}
