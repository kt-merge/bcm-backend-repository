package com.example.chicken.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.chicken.dto.mail.AuctionWonEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuctionEmailListener {

	private final EmailService emailService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleAuctionWon(AuctionWonEvent event) {
		this.emailService.sendAuctionSuccessEmail(event.email(), event.productName());
	}

}
