package com.example.chicken.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.chicken.domain.auth.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, String> {
}
