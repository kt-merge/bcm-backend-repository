package com.example.chicken.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.dto.product.ProductRequestDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto request) {
		ProductResponseDto result = this.productService.createProduct(request);

		URI location = URI.create(result.id().toString());

		return ResponseEntity.created(location).body(result);
	}
}
