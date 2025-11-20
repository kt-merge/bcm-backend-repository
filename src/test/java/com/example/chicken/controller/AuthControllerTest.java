package com.example.chicken.controller;

import com.example.chicken.ChickenApplication;
import com.example.chicken.domain.Role;
import com.example.chicken.domain.User;
import com.example.chicken.dto.SignInRequestDto;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = ChickenApplication.class)
class AuthControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	User user;

	@BeforeEach
	void setUp() {
		String email = "yoojinlee.dev@gmail.com";
		String password = "1q2w3e4r!";
		String nickname = "yoojinLee";
		Role role = Role.USER;

		this.user = User.builder()
			.email(email)
			.password(this.passwordEncoder.encode(password))
			.nickname(nickname)
			.role(role)
			.build();

		this.userRepository.save(user);
	}

	@Test
	@DisplayName("회원가입 성공")
	void signUp() throws Exception {
		//given
		String phoneNumber = "01012345678";
		UserRequestDto request =
			new UserRequestDto("test@test.com", "1q2w3e4r!", "testnick", phoneNumber);
		//when
		mockMvc.perform(post("/api/auth/sign-up")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
			//then
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.email").exists())
			.andExpect(jsonPath("$.nickname").exists())
			.andExpect(jsonPath("$.phoneNumber").value(phoneNumber));
	}

	@Test
	@DisplayName("회원가입 Validation 검증")
	void signUp_validation() throws Exception {
		//given
		UserRequestDto request = UserRequestDto.newInstance();

		//when
		mockMvc.perform(post("/api/auth/sign-up")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
			//then
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.message").value("Field validation fail"))
			.andExpect(jsonPath("$.errors").isArray());
	}

	@Test
	@DisplayName("로그인 성공")
	void signIn() throws Exception {
		SignInRequestDto request = new SignInRequestDto(user.getEmail(), "1q2w3e4r!");

		//when
		mockMvc.perform(post("/api/auth/sign-in")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
			//then
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken").exists());
	}

}