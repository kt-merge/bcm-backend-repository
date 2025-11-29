package com.example.chicken.domain.order.dto;

public record ShippingInfoRequestDto(String name, String phoneNumber, String zipCode, String address,
									 String detailAddress) {

}
