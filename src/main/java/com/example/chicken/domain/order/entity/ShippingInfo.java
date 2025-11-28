package com.example.chicken.domain.order.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInfo {
	private String name;
	private String phoneNumber;
	private String zipCode;
	private String address;
	private String detailAddress;

	@Builder
	private ShippingInfo(String name, String phoneNumber, String zipCode, String address, String detailAddress) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.zipCode = zipCode;
		this.address = address;
		this.detailAddress = detailAddress;
	}

}
