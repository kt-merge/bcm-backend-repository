package com.example.chicken.controller;

import static com.example.chicken.common.constant.PathConstant.User.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.dto.user.UpdateUserInfoDto;
import com.example.chicken.service.ProductService;
import com.example.chicken.domain.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_PREFIX)
public class UserController {

	private final UserService userService;
	private final ProductService productService;

	@GetMapping(ME)
	public ResponseEntity<UserResponseDto> getUserInfo() {
		UserResponseDto result = this.userService.getUserInfo();
		return ResponseEntity.ok(result);
	}

	@PutMapping(ME)
	public ResponseEntity<UserResponseDto> updateUserInfo(@RequestBody UpdateUserInfoDto request) {
		UserResponseDto result = this.userService.updateUserInfo(request);
		return ResponseEntity.ok(result);
	}

	@GetMapping(MY_PRODUCTS)
	public ResponseEntity<List<ProductResponseDto>> getUserProducts() {
		return ResponseEntity.ok(this.productService.getMyProducts());
	}

}
