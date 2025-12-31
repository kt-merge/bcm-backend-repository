package com.example.chicken.domain.product.dto;

import com.example.chicken.domain.product.entity.BidStatus;
import java.util.List;

public record ProductSearchCondition(Long id, String name, Long categoryId, List<BidStatus> bidStatus) {
}
