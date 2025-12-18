package com.example.chicken.domain.product.dto;

import java.time.LocalDateTime;

public record CategoryCountResponseDto(Long id,
                                       String code,
                                       String name,
                                       LocalDateTime createdAt,
                                       LocalDateTime modifiedAt,
                                       Long productCount) {
}
