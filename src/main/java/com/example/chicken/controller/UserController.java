package com.example.chicken.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserResponseDto> getUserInfo() {
		UserResponseDto result = this.userService.getUserInfo();
		return ResponseEntity.ok(result);
	}

}
