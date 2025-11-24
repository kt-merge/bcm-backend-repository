package com.example.chicken.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.chicken.common.constant.BidConstant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidScheduleService {

	private final StringRedisTemplate redisTemplate;

	public void register(long productId, LocalDateTime endAt) {
		ZonedDateTime endAtKst = endAt.atZone(ZoneId.of("Asia/Seoul"));

		long timeStamp = endAtKst
			.withZoneSameInstant(ZoneOffset.UTC)
			.toInstant()
			.toEpochMilli();

		redisTemplate.opsForZSet().add(BidConstant.AUCTION_SCHEDULE_KEY, Long.toString(productId), timeStamp);

		log.info("Registered auction schedule for productId: {} at {}", productId, endAt);
	}
}
