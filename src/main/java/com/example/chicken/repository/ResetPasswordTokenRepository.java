package com.example.chicken.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.chicken.domain.auth.entity.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, String> {
	Optional<ResetPasswordToken> findByResetToken(String resetToken);
}
