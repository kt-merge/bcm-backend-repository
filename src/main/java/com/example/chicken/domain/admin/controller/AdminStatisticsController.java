package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_STATISTICS_BIDS_DAILY_REGISTRATIONS;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_STATISTICS_PREFIX;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_STATISTICS_PRODUCTS_DAILY_REGISTRATIONS;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_STATISTICS_USERS_DAILY_REGISTRATIONS;

import com.example.chicken.domain.admin.dto.DailyBidRegistrationCountDto;
import com.example.chicken.domain.admin.dto.DailyProductRegistrationCountDto;
import com.example.chicken.domain.admin.dto.DailyUserRegistrationCountDto;
import com.example.chicken.domain.admin.service.StatisticsService;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_STATISTICS_PREFIX)
public class AdminStatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping(ADMIN_STATISTICS_USERS_DAILY_REGISTRATIONS)
    public ResponseEntity<List<DailyUserRegistrationCountDto>> getDailyUserRegistrationCounts(
            @RequestParam(defaultValue = "7") @Positive Integer daysAgo) {

        List<DailyUserRegistrationCountDto> result = this.statisticsService.getDailyUserRegistrationCounts(daysAgo);

        return ResponseEntity.ok(result);
    }

    @GetMapping(ADMIN_STATISTICS_PRODUCTS_DAILY_REGISTRATIONS)
    public ResponseEntity<List<DailyProductRegistrationCountDto>> getDailyProductRegistrationCounts(
            @RequestParam(defaultValue = "7") @Positive Integer daysAgo) {
        List<DailyProductRegistrationCountDto> result = this.statisticsService.getDailyProductRegistrationCounts(
                daysAgo);

        return ResponseEntity.ok(result);
    }

    @GetMapping(ADMIN_STATISTICS_BIDS_DAILY_REGISTRATIONS)
    public ResponseEntity<List<DailyBidRegistrationCountDto>> getDailyBidRegistrationCounts(
            @RequestParam(defaultValue = "7") @Positive Integer daysAgo) {
        List<DailyBidRegistrationCountDto> result = this.statisticsService.getDailyBidCounts(daysAgo);

        return ResponseEntity.ok(result);
    }

}
