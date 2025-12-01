package com.example.chicken.domain.payment.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentRequestDto(@NotNull Long orderId) {
}
