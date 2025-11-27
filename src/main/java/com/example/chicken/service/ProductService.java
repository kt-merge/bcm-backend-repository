package com.example.chicken.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.exception.ProductNotFoundException;
import com.example.chicken.dto.product.ProductBidInfoResponseDto;
import com.example.chicken.dto.product.ProductBidRequestDto;
import com.example.chicken.dto.product.ProductRequestDto;
import com.example.chicken.dto.product.ProductResponseDto;
import com.example.chicken.repository.ProductBidRepository;
import com.example.chicken.domain.auth.repository.ProductRepository;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Value("${s3.product-bucket-url}")
	private String s3BucketUrl;

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ProductBidRepository productBidRepository;
	private final BidScheduleService bidScheduleService;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto request) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

		String imageUrl = s3BucketUrl + request.imageUrl();

		Product product = Product.builder()
			.name(request.name())
			.description(request.description())
			.category(request.category())
			.startPrice(request.price())
			.bidPrice(request.price())
			.bidCount(0L)
			.bidStatus(BidStatus.NOT_BIDDED)
			.productStatus(request.productStatus())
			.bidEndDate(request.bidEndDate())
			.imageUrl(imageUrl)
			.user(user)
			.build();

		Product savedProduct = this.productRepository.save(product);

		this.bidScheduleService.register(savedProduct.getId(), savedProduct.getBidEndDate());

		return ProductResponseDto.from(savedProduct);
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

		if (product.isBidInactive()) product.activeBid();

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
