package com.example.chicken.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.product.ProductBid;

public interface ProductBidRepository extends JpaRepository<ProductBid, Long> {

	@EntityGraph(attributePaths = {"user", "product"})
	Optional<ProductBid> findTopByProductIdOrderByCreatedAtDesc(Long productId);

	@EntityGraph(attributePaths = {"user", "product"})
	List<ProductBid> findTop5ByProductIdOrderByCreatedAtDesc(Long productId);

}
