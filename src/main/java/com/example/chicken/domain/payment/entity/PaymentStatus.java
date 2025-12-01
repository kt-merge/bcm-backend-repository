package com.example.chicken.domain.payment.entity;

import lombok.Getter;

@Getter
public enum PaymentStatus {
	READY, REQUESTED, PAID, FAILED, CANCELED, REFUNDED, PARTIALLY_REFUNDED;
}
