package com.example.chicken.domain.payment.event;

import static org.springframework.transaction.annotation.Propagation.*;
import static org.springframework.transaction.event.TransactionPhase.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCreateRequestedListener {

	@Transactional(propagation = REQUIRES_NEW)
	@TransactionalEventListener(phase = AFTER_COMMIT)
	public void handle(PaymentCreateRequestedEvent event) {
		log.info(event.toString());
	}

}
