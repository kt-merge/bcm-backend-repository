package com.example.chicken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.product.ProductBid;

public interface ProductBidRepository extends JpaRepository<ProductBid, Long> {
	Optional<ProductBid> findTopByProductIdOrderByCreatedAtDesc(Long productId);
}
