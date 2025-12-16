package com.example.chicken.domain.product.repository;

import com.example.chicken.domain.admin.dto.DailyBidRegistrationCountDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductBidRepositoryCustom {
    List<DailyBidRegistrationCountDto> countBidsByDay(LocalDateTime startDate);
}
