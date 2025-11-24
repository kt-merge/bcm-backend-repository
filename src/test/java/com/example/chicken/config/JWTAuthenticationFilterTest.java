package com.example.chicken.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class JWTAuthenticationFilterTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("JWTAuthenticationFilter - Invalid Token Test")
	void SecurityFilterTest() throws Exception {
		String invalidToken = "eyJhbGciOiJIUzI1NiJ9.invalid.signature";

		mockMvc.perform(post("/api/products")
							.header("Authorization", "Bearer " + invalidToken)
			)
			.andDo(print())
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.status").value("401"))
			.andExpect(jsonPath("$.message").exists());
	}

}