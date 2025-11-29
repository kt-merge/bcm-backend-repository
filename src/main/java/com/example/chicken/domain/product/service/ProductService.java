package com.example.chicken.domain.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.exception.ProductNotFoundException;
import com.example.chicken.domain.product.repository.ProductRepository;
import com.example.chicken.domain.product.dto.ProductBidInfoResponseDto;
import com.example.chicken.domain.product.dto.ProductBidRequestDto;
import com.example.chicken.domain.product.dto.ProductRequestDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.repository.ProductBidRepository;
import com.example.chicken.service.BidScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Value("${s3.product-bucket-url}")
	private String s3BucketUrl;

	private final UserRepository userRepository;
	private final ProductMapper productMapper;
	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;
	private final BidScheduleService bidScheduleService;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto request) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		String imageUrl = s3BucketUrl + request.imageUrl();

		User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

		Product product = this.productMapper.toEntity(request, imageUrl, user);

		Product savedProduct = this.productRepository.save(product);

		this.bidScheduleService.register(savedProduct.getId(), savedProduct.getBidEndDate());

		return this.productMapper.toResponseDto(product);
	}

	@Transactional(readOnly = true)
	public ProductResponseDto getProduct(Long productId) {

		Product product = this.productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId.toString()));

		List<ProductBidInfoResponseDto> productBidResponses = this.productBidRepository
			.findTop5ByProductIdOrderByCreatedAtDesc(productId)
			.stream().map(ProductBidInfoResponseDto::from)
			.toList();

		return ProductResponseDto.of(product, productBidResponses);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getProducts(Pageable pageable) {
		Page<Product> products = this.productRepository.findAll(pageable);

		return products.map(ProductResponseDto::from);
	}

	@Transactional(readOnly = true)
	public List<ProductResponseDto> getMyProducts() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = this.userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(email));

		return this.productRepository.findTop10ByUserOrderByCreatedAtDesc(user)
			.stream().map(ProductResponseDto::from)
			.toList();
	}

	@Transactional
	public boolean updateProductBid(Long productId, ProductBidRequestDto request) {
		String email = request.email();
		BigDecimal price = request.price();

		User user = this.userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(email));

		Product product = this.productRepository.findById(productId)
			.orElseThrow(() -> new ProductNotFoundException(productId.toString()));

		if (product.isBidInactive())
			product.activeBid();

		if (product.isBidPriceLowerThan(price)) {
			product.updateBidPrice(price);
			product.incrementBidCount();

			ProductBid productBid = ProductBid.builder()
				.price(price)
				.user(user)
				.product(product)
				.build();

			this.productBidRepository.save(productBid);

			return true;
		}

		return false;
	}

}
