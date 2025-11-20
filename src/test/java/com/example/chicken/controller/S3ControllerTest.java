// package com.example.chicken.controller;
//
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.chicken.ChickenApplication;
// import com.example.chicken.common.jwt.JwtTokenProvider;
// import com.example.chicken.domain.Role;
// import com.example.chicken.domain.User;
// import com.example.chicken.repository.UserRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @Transactional
// @AutoConfigureMockMvc
// @SpringBootTest(classes = ChickenApplication.class)
// class S3ControllerTest {
//
// 	@Autowired
// 	MockMvc mockMvc;
//
// 	@Autowired
// 	ObjectMapper objectMapper;
//
// 	@Autowired
// 	PasswordEncoder passwordEncoder;
//
// 	@Autowired
// 	UserRepository userRepository;
//
// 	@Autowired
// 	JwtTokenProvider tokenProvider;
//
// 	User user;
//
// 	String token;
//
// 	@BeforeEach
// 	void setUp() {
// 		String email = "yoojinlee.dev@gmail.com";
// 		String password = "1q2w3e4r!";
// 		String nickname = "yoojinLee";
// 		String phoneNumber = "01012345678";
// 		Role role = Role.USER;
//
// 		this.user = User.builder()
// 			.email(email)
// 			.password(this.passwordEncoder.encode(password))
// 			.nickname(nickname)
// 			.role(role)
// 			.phoneNumber(phoneNumber)
// 			.build();
//
// 		this.userRepository.save(user);
//
// 		token = "Bearer " + tokenProvider.createJWT(email, role);
// 	}
//
// 	@Test
// 	@DisplayName("S3 업로드 URL 생성 성공")
// 	void putUploadUrl() throws Exception {
// 		mockMvc.perform(get("/api/s3/upload-url")
// 							.header("Authorization", this.token)
// 							.contentType(MediaType.APPLICATION_JSON))
// 			//then
// 			.andDo(print())
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.url").exists())
// 			.andExpect(jsonPath("$.url").isNotEmpty());
// 	}
//
// 	@Test
// 	@DisplayName("S3 다운로드 URL 생성 성공")
// 	void getDownloadUrl() throws Exception {
// 		mockMvc.perform(get("/api/s3/download-url")
// 							.header("Authorization", this.token)
// 							.contentType(MediaType.APPLICATION_JSON))
// 			//then
// 			.andDo(print())
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.url").exists())
// 			.andExpect(jsonPath("$.url").isNotEmpty());
// 	}
//
// }