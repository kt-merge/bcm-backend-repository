package com.example.chicken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chicken.domain.product.HighestBidder;

public interface HighestBidderRepository extends JpaRepository<HighestBidder, Long> {
}
