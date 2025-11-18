package com.example.chicken.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import com.example.chicken.scheduler.AuctionEndJob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSchedulerService {

	private final Scheduler scheduler;

	public void scheduleAuctionEnd(Long productId, LocalDateTime endTime) {
		JobDetail jobDetail = JobBuilder.newJob(AuctionEndJob.class)
			.withIdentity("product-auction-end-" + productId, "product-auction-end-group")
			.usingJobData("productId", productId)
			.build();

		Trigger trigger = TriggerBuilder.newTrigger()
			.withIdentity("product-auction-end-trigger-" + productId, "product-auction-end-trigger-group")
			// 한국 시간
			.startAt(Date.from(endTime.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
			.build();

		try {
			log.info("Scheduling auction end job for productId: {} at {}", productId, endTime);
			this.scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException("스케줄러 등록 실패", e);
		}
	}

}
