package com.example.chicken.domain.product.service;

import com.example.chicken.domain.admin.dto.DailyProductRegistrationCountDto;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.auth.service.UserMapper;
import com.example.chicken.domain.product.dto.ProductBidInfoResponseDto;
import com.example.chicken.domain.product.dto.ProductBidRequestDto;
import com.example.chicken.domain.product.dto.ProductImageResponseDto;
import com.example.chicken.domain.product.dto.ProductRequestDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.dto.ProductSearchCondition;
import com.example.chicken.domain.product.dto.ProductUpdateRequestDto;
import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductBid;
import com.example.chicken.domain.product.entity.ProductImage;
import com.example.chicken.domain.product.exception.CategoryNotFoundException;
import com.example.chicken.domain.product.exception.ProductNotFoundException;
import com.example.chicken.domain.product.repository.CategoryRepository;
import com.example.chicken.domain.product.repository.ProductBidRepository;
import com.example.chicken.domain.product.repository.ProductRepository;
import com.example.chicken.service.BidScheduleService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${s3.product-bucket-url}")
    private String s3BucketUrl;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductBidRepository productBidRepository;
    private final CategoryRepository categoryRepository;

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final ProductImageMapper productImageMapper;

    private final BidScheduleService bidScheduleService;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        Category category = this.categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId().toString()));

        Product product = this.productMapper.toEntity(request, user, category);

        List<ProductImage> productImages = request.imageUrls()
                .stream()
                .map(url -> productImageMapper.toEntity(product, this.s3BucketUrl + url))
                .collect((Collectors.toList()));

        product.updateImages(productImages);

        Product savedProduct = this.productRepository.save(product);

        this.bidScheduleService.register(savedProduct.getId(), savedProduct.getBidEndDate());

        List<ProductImageResponseDto> productImageResponseDtoList = productImages
                .stream()
                .map(this.productImageMapper::toResponseDto)
                .toList();

        return this.productMapper.toResponseDto(savedProduct, userMapper.toResponse(user),
                categoryMapper.toResponseDto(category), productImageResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        List<ProductBidInfoResponseDto> productBidResponses = this.productBidRepository
                .findTop5ByProductIdOrderByCreatedAtDesc(productId)
                .stream().map(ProductBidInfoResponseDto::from)
                .toList();

        List<ProductImageResponseDto> productImageResponseDtoList = product.getImages()
                .stream()
                .map(this.productImageMapper::toResponseDto)
                .toList();

        return this.productMapper.toResponseDto(product, productBidResponses, userMapper.toResponse(product.getUser()),
                categoryMapper.toResponseDto(product.getCategory()), productImageResponseDtoList);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(ProductSearchCondition condition, Pageable pageable) {
        Page<Product> result = this.productRepository.searchProducts(condition, pageable);

        return result.map(product -> {

                    List<ProductImageResponseDto> productImageResponseDtoList = product.getImages()
                            .stream()
                            .map(this.productImageMapper::toResponseDto)
                            .toList();

                    return productMapper.toResponseDto(product,
                            userMapper.toResponse(product.getUser()),
                            categoryMapper.toResponseDto(product.getCategory()),
                            productImageResponseDtoList);
                }
        );
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getMyProducts() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return this.productRepository.findTop10ByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(product -> {
                    List<ProductImageResponseDto> productImageResponseDtoList = product.getImages()
                            .stream()
                            .map(this.productImageMapper::toResponseDto)
                            .toList();
                    return productMapper.toResponseDto(product, userMapper.toResponse(product.getUser()),
                            categoryMapper.toResponseDto(product.getCategory()), productImageResponseDtoList);
                })
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

        if (product.isBidInactive()) {
            product.activeBid();
        }

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

    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductUpdateRequestDto request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId().toString()));

        List<ProductImage> productImages = request.imageUrls()
                .stream()
                .map(url -> {
                    if (url.startsWith("http")) {
                        return productImageMapper.toEntity(product, url);
                    } else {
                        return productImageMapper.toEntity(product, s3BucketUrl + url);
                    }
                })
                .collect((Collectors.toList()));

        product.updateProduct(request.name(),
                request.description(),
                category,
                request.productStatus(),
                request.bidEndDate(),
                productImages
        );

        List<ProductImageResponseDto> productImageResponseDtoList = productImages
                .stream()
                .map(this.productImageMapper::toResponseDto)
                .toList();

        return this.productMapper.toResponseDto(product, userMapper.toResponse(product.getUser()),
                categoryMapper.toResponseDto(product.getCategory()), productImageResponseDtoList);
    }

    @Transactional(readOnly = true)
    public List<DailyProductRegistrationCountDto> getDailyProductRegistrationCounts(Integer daysAgo) {
        LocalDateTime startDate = LocalDate.now().minusDays(daysAgo - 1L).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().plusDays(1L).atStartOfDay();

        return this.productRepository.countProductsByDay(startDate, endDate);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId.toString()));

        this.productRepository.delete(product);
    }

}
