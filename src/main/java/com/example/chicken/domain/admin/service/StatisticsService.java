package com.example.chicken.domain.admin.service;

import com.example.chicken.domain.admin.dto.DailyProductRegistrationCountDto;
import com.example.chicken.domain.admin.dto.DailyUserRegistrationCountDto;
import com.example.chicken.domain.auth.service.UserService;
import com.example.chicken.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserService userService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<DailyUserRegistrationCountDto> getDailyUserRegistrationCounts(Integer daysAgo) {
        return this.userService.getDailyUserRegistrationCounts(daysAgo);
    }

    @Transactional(readOnly = true)
    public List<DailyProductRegistrationCountDto> getDailyProductRegistrationCounts(Integer daysAgo) {
        return this.productService.getDailyProductRegistrationCounts(daysAgo);
    }
}