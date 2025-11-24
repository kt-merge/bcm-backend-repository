package com.example.chicken.scheduler;

import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.chicken.common.constant.BidConstant;
import com.example.chicken.service.AuctionEndJobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionSchedulerWorker {

	private final StringRedisTemplate redisTemplate;
	private final AuctionEndJobService auctionEndJobService;

	@Scheduled(fixedRate = 1000)
	public void checkSchedules() {
		long now = System.currentTimeMillis();

		Set<String> readyJobs =
			this.redisTemplate.opsForZSet().rangeByScore(BidConstant.AUCTION_SCHEDULE_KEY, 0, now);

		if(readyJobs == null || readyJobs.isEmpty()) return;

		for (String p : readyJobs) {
			endProductAuction(p);
		}
	}

	private void endProductAuction(String p) {
		long productId = Long.parseLong(p);

		this.auctionEndJobService.endProductAuction(productId);

		redisTemplate.opsForZSet().remove(BidConstant.AUCTION_SCHEDULE_KEY, p);
		log.info("Ended auction for productId: {}", productId);
	}

}
