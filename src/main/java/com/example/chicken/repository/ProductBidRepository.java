package com.example.chicken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.product.ProductBid;

public interface ProductBidRepository extends JpaRepository<ProductBid, Long> {

	@EntityGraph(attributePaths = {"user", "product"})
	Optional<ProductBid> findTopByProductIdOrderByCreatedAtDesc(Long productId);
}
