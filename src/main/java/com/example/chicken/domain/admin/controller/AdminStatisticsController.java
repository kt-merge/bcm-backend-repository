package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_STATISTICS_PREFIX;
import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_STATISTICS_USERS_DAILY_REGISTRATIONS;

import com.example.chicken.domain.auth.dto.user.DailyUserRegistrationCountDto;
import com.example.chicken.domain.auth.service.UserService;
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

    private final UserService userService;

    @GetMapping(ADMIN_STATISTICS_USERS_DAILY_REGISTRATIONS)
    public ResponseEntity<List<DailyUserRegistrationCountDto>> getDailyUserRegistrationCounts(
            @RequestParam(defaultValue = "7") @Positive Integer daysAgo) {

        List<DailyUserRegistrationCountDto> result = this.userService.getDailyUserRegistrationCounts(daysAgo);

        return ResponseEntity.ok(result);
    }
}
