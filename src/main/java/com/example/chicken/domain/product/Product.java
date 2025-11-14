package com.example.chicken.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.chicken.common.entity.BaseTimeEntity;
import com.example.chicken.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 500)
	private String name;

	@Column(length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	private Category category;

	private BigDecimal startPrice;

	private BigDecimal bidPrice;

	@Enumerated(EnumType.STRING)
	private BidStatus bidStatus;

	private Long bidCount;

	private LocalDateTime bidEndDate;

	@Enumerated(EnumType.STRING)
	private ProductStatus productStatus;

	@Column(length = 1000)
	private String imageUrl;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Builder
	private Product(String name,
					String description,
					Category category,
					BigDecimal startPrice,
					BigDecimal bidPrice,
					Long bidCount,
					LocalDateTime bidEndDate,
					BidStatus bidStatus,
					ProductStatus productStatus,
					String imageUrl,
					User user) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.startPrice = startPrice;
		this.bidPrice = bidPrice;
		this.bidCount = bidCount;
		this.bidStatus = bidStatus;
		this.bidEndDate = bidEndDate;
		this.productStatus = productStatus;
		this.imageUrl = imageUrl;
		this.user = user;
	}

	public boolean isBidPriceLowerThan(BigDecimal price) {
		return this.bidPrice.compareTo(price) < 0;
	}

	public boolean isBidUnactive() {
		return this.bidCount == 0L;
	}

	public void activeBid() {
		this.bidStatus = BidStatus.BIDDED;
	}

	public void updateBidPrice(BigDecimal price) {
		this.bidPrice = price;
	}

	public void incrementBidCount() {
		if (this.bidCount == null)
			this.bidCount = 0L;
		this.bidCount += 1;
	}
}
