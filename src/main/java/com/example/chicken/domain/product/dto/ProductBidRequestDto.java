package com.example.chicken.domain.product.dto;

import java.math.BigDecimal;

public record ProductBidRequestDto(BigDecimal price, String email) {
}
