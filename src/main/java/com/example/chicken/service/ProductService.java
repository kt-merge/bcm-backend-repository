package com.example.chicken.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.User;
import com.example.chicken.domain.product.Product;
import com.example.chicken.domain.product.ProductBid;
import com.example.chicken.dto.product.ProductBidRequestDto;
import com.example.chicken.dto.product.ProductRequestDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.repository.ProductBidRepository;
import com.example.chicken.repository.ProductRepository;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto request) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = this.userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		Product product = Product.builder()
			.name(request.name())
			.description(request.description())
			.category(request.category())
			.startPrice(request.price())
			.bidPrice(request.price())
			.bidCount(0L)
			.productStatus(request.productStatus())
			.imageUrl(request.imageUrl())
			.user(user)
			.build();

		Product savedProduct = this.productRepository.save(product);

		return ProductResponseDto.from(savedProduct);
	}

	@Transactional(readOnly = true)
	public ProductResponseDto getProduct(Long productId) {
		Product product = this.productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		return ProductResponseDto.from(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getProducts(Pageable pageable) {
		Page<Product> products = this.productRepository.findAll(pageable);

		return products.map(ProductResponseDto::from);
	}

	@Transactional
	public boolean updateProductBid(Long productId, ProductBidRequestDto request) {

		User user = this.userRepository.findByEmail(request.email())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		Product product = this.productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		if (product.isBidPriceLowerThan(request.price())) {
			product.updateBidPrice(request.price());
			product.incrementBidCount();

			Product savedProduct = this.productRepository.save(product);

			ProductBid productBid = ProductBid.builder()
				.price(request.price())
				.user(user)
				.product(savedProduct)
				.build();

			this.productBidRepository.save(productBid);

			return true;
		}

		return false;
	}

}
