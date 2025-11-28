package com.example.chicken.domain.product.controller;

import static com.example.chicken.common.constant.PathConstant.Product.*;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.dto.product.ProductRequestDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.domain.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT_PREFIX)
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto request) {
		ProductResponseDto result = this.productService.createProduct(request);

		URI location = URI.create(result.id().toString());

		return ResponseEntity.created(location).body(result);
	}

	@GetMapping
	public ResponseEntity<Page<ProductResponseDto>> getProducts(Pageable pageable) {

		Page<ProductResponseDto> result = this.productService.getProducts(pageable);

		return ResponseEntity.ok(result);
	}

	@GetMapping(PRODUCT_ID)
	public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("productId") Long productId) {

		ProductResponseDto result = this.productService.getProduct(productId);

		return ResponseEntity.ok(result);
	}
}
