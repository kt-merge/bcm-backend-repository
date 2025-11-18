package com.example.chicken.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.chicken.service.AuctionEndJobService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuctionEndJob implements Job {

	@Autowired
	AuctionEndJobService auctionEndJobService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long productId = jobExecutionContext.getJobDetail().getJobDataMap().getLong("productId");
		log.info("Executing auction end job for productId: {}", productId);
		this.auctionEndJobService.endProductAuction(productId);
	}

}
