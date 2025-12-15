package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.BidStatus;

public record ProductSearchCondition(Long id, String name, Long categoryId, BidStatus bidStatus) {
}
