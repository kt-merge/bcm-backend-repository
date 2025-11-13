package com.example.chicken.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.dto.UpdateUserNicknameDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.service.ProductService;
import com.example.chicken.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {

	private final UserService userService;
	private final ProductService productService;

	@GetMapping("/me")
	public ResponseEntity<UserResponseDto> getUserInfo() {
		UserResponseDto result = this.userService.getUserInfo();
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/me/nickname")
	public ResponseEntity<UserResponseDto> updateNickname(@RequestBody UpdateUserNicknameDto request) {
		UserResponseDto result = this.userService.updateNickname(request);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/me/products")
	public ResponseEntity<List<ProductResponseDto>> getUserProducts() {
		return ResponseEntity.ok(this.productService.getMyProducts());
	}

}
