package com.example.chicken.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.example.chicken.ChickenApplication;
import com.example.chicken.common.jwt.JwtTokenProvider;
import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.entity.token.RefreshToken;
import com.example.chicken.dto.SignInRequestDto;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.domain.auth.repository.RefreshTokenRepository;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;

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

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	RefreshTokenRepository tokenRepository;

	User user;

	String refreshToken;

	@BeforeEach
	void setUp() {
		String email = "yoojinlee.dev@gmail.com";
		String password = "1q2w3e4r!";
		String nickname = "yoojinLee";
		Role role = Role.USER;

		User userValue = User.builder()
			.email(email)
			.password(this.passwordEncoder.encode(password))
			.nickname(nickname)
			.role(role)
			.build();

		user = this.userRepository.save(userValue);

		this.refreshToken = this.tokenProvider.createTokens(user.getEmail(), user.getNickname()).refreshToken();

		RefreshToken refreshToken = RefreshToken.builder()
			.email(user.getEmail())
			.refreshToken(this.refreshToken)
			.build();

		this.tokenRepository.save(refreshToken);

	}

	@Test
	@DisplayName("[Integration Test] 회원가입 성공")
	void signUp() throws Exception {
		//given
		String email = "test@test.com";
		String password = "1q2w3e4r!";
		String nickname = "testnick";
		String phoneNumber = "01012345678";

		UserRequestDto request =
			new UserRequestDto(email, password, nickname, phoneNumber);

		//when
		mockMvc.perform(post("/api/auth/sign-up")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
			//then
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.email").value(email))
			.andExpect(jsonPath("$.role").value(Role.USER.name()))
			.andExpect(jsonPath("$.nickname").value(nickname))
			.andExpect(jsonPath("$.phoneNumber").value(phoneNumber))
			.andExpect(jsonPath("$.createdAt").exists())
			.andExpect(jsonPath("$.modifiedAt").exists());

	}

	@Test
	@DisplayName("[Integration Test] 회원가입 시 유효성 검증 실패")
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
	@DisplayName("[Integration Test] 로그인 성공")
	void signIn() throws Exception {
		SignInRequestDto request = new SignInRequestDto(user.getEmail(), "1q2w3e4r!");

		//when
		mockMvc.perform(post("/api/auth/sign-in")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
			//then
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(cookie().exists("refresh-token"))
			.andExpect(cookie().value("refresh-token", notNullValue()))
			.andExpect(jsonPath("$.accessToken").exists());
	}

	@Test
	@DisplayName("토큰 재발급 성공")
	void reissue() throws Exception {
		//given
		String refreshToken = this.refreshToken;
		Cookie cookie = new Cookie("refresh-token", refreshToken);

		//when
		mockMvc.perform(post("/api/auth/reissue")
							.cookie(cookie)
							.contentType(MediaType.APPLICATION_JSON))
			//then
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(cookie().exists("refresh-token"))
			.andExpect(jsonPath("$.accessToken").exists());
	}

	@Test
	@DisplayName("토큰 재발급 시 유효성 검증 실패")
	void reissue_validate() throws Exception {
		//given
		String authValue = "";
		Cookie cookie = new Cookie("refresh-token", authValue);

		//when
		mockMvc.perform(post("/api/auth/reissue")
							.cookie(cookie)
							.contentType(MediaType.APPLICATION_JSON))
			//then
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

}
