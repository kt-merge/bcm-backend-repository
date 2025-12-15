package com.example.chicken.domain.product.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductBidRequestDto(BigDecimal price, String email) {
}
