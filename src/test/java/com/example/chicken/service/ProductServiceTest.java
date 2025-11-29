package com.example.chicken.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.product.entity.BidStatus;
import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.entity.Product;
import com.example.chicken.domain.product.entity.ProductStatus;
import com.example.chicken.domain.product.service.ProductService;
import com.example.chicken.domain.product.dto.ProductRequestDto;
import com.example.chicken.domain.product.dto.ProductResponseDto;
import com.example.chicken.domain.product.repository.ProductRepository;
import com.example.chicken.domain.auth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductRepository productRepository;

	@Mock
	BidScheduleService bidScheduleService;

	@Mock
	UserRepository userRepository;

	@InjectMocks
	ProductService productService;

	@BeforeEach
	void setUp() {
		Authentication auth = mock(Authentication.class);
		when(auth.getName()).thenReturn("test@email.com");

		SecurityContext context = mock(SecurityContext.class);
		when(context.getAuthentication()).thenReturn(auth);

		SecurityContextHolder.setContext(context);

		ReflectionTestUtils.setField(productService, "s3BucketUrl", "https://my-s3/");
	}

	@Test
	@DisplayName("[Service Test] 상품생성 성공")
	void createProduct() {
		//given
		ProductRequestDto request = new ProductRequestDto("Test Product",
														  "This is a test product",
														  Category.ETC,
														  BigDecimal.valueOf(150000),
														  ProductStatus.GOOD,
														  LocalDateTime.now().plusDays(10),
														  "image.jpg");

		String email = "test@email.com";

		User user = User.builder()
			.email(email)
			.nickname("testuser")
			.password("password")
			.phoneNumber("010-1234-5678")
			.build();

		ReflectionTestUtils.setField(user, "id", 1L);

		when(this.userRepository.findByEmail(email))
			.thenReturn(Optional.of(user));

		Product saved = Product.builder()
			.name("Test Product")
			.description("This is a test product")
			.category(Category.ETC)
			.startPrice(BigDecimal.valueOf(150000))
			.bidPrice(BigDecimal.valueOf(150000))
			.bidCount(0L)
			.bidStatus(BidStatus.NOT_BIDDED)
			.productStatus(ProductStatus.GOOD)
			.bidEndDate(request.bidEndDate())
			.imageUrl("https://my-s3/" + request.imageUrl())
			.user(user)
			.build();

		ReflectionTestUtils.setField(saved, "id", 1L);

		doNothing().when(this.bidScheduleService)
			.register(anyLong(), any(LocalDateTime.class));

		when(this.productRepository.save(any(Product.class)))
			.thenReturn(saved);
		//when
		ProductResponseDto result = this.productService.createProduct(request);

		//then
		assertNotNull(result);
		verify(this.userRepository, times(1)).findByEmail(email);
		verify(this.productRepository, times(1)).save(any(Product.class));
		verify(this.bidScheduleService, times(1))
			.register(eq(1L), any(LocalDateTime.class));
	}
}