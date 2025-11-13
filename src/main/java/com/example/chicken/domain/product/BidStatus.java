package com.example.chicken.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BidStatus {
	NOT_BIDDED("입찰 전"),
	BIDDED("입찰 중"),
	COMPLETED("완료");

	private final String description;
}