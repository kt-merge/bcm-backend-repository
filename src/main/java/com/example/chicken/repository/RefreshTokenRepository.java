package com.example.chicken.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.chicken.domain.auth.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
