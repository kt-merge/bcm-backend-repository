package com.example.chicken.domain.product.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CategoryResponseDto(Long id,
                                  String code,
                                  String name,
                                  LocalDateTime createdAt,
                                  LocalDateTime modifiedAt) {
}
