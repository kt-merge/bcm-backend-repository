package com.example.chicken.common.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.entity.user.UserStatus;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.domain.product.entity.Category;
import com.example.chicken.domain.product.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		String adminNickname = "Admin";
		String adminEmail = "admin@admin.com";
		String adminPassword = "1q2w3e4r!";

		if (!this.userRepository.existsByEmail(adminEmail)) {
			User admin = User.builder()
				.nickname(adminNickname)
				.email(adminEmail)
				.password(passwordEncoder.encode(adminPassword))
				.role(Role.ADMIN)
				.status(UserStatus.ACTIVE)
				.build();

			this.userRepository.save(admin);
		}

		String code = "ETC";
		String name = "기타";

		if (!this.categoryRepository.existsByCode(code)) {
			Category category = Category.builder()
				.code(code)
				.name(name)
				.build();

			this.categoryRepository.save(category);
		}

	}
}
