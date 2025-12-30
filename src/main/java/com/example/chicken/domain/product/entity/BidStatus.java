package com.example.chicken.domain.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BidStatus {
    NOT_BIDDED("입찰 전"),
    BIDDED("입찰 중"),
    NO_BIDDER("낙찰자 없음"),
    PAYMENT_WAITING("입금 대기"),
    COMPLETED("완료");

    private final String description;
}