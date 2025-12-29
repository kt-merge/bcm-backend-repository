package com.example.chicken.domain.payment.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequestDto(
        @NotNull String paymentKey,
        @NotNull String orderId,
        @NotNull BigDecimal amount
) {
}
