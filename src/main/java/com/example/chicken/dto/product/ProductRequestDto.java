package com.example.chicken.dto.product;

import java.math.BigDecimal;

import com.example.chicken.domain.product.Category;
import com.example.chicken.domain.product.ProductStatus;

public record ProductRequestDto(String name, String description, Category category, BigDecimal price,
								ProductStatus productStatus, String imageUrl) {
}
