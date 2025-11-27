package com.example.chicken.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.product.entity.HighestBidder;

public interface HighestBidderRepository extends JpaRepository<HighestBidder, Long> {

	@EntityGraph(attributePaths = {"product"})
	List<HighestBidder> findByWinnerId(Long winnerId);
}
