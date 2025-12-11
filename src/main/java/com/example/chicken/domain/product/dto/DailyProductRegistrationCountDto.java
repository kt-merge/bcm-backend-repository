package com.example.chicken.domain.product.dto;

import java.time.LocalDate;

public record DailyProductRegistrationCountDto(LocalDate date, Long count) {
}
