package com.example.chicken.controller;

import java.net.URI;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.common.constant.CookieConstant;
import com.example.chicken.common.jwt.annotation.IsJwt;
import com.example.chicken.common.util.CookieUtil;
import com.example.chicken.dto.SignInRequestDto;
import com.example.chicken.dto.SignInResponseDto;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.auth.ResetPasswordRequestDto;
import com.example.chicken.dto.user.AccessTokenResponseDto;
import com.example.chicken.dto.user.TokenResponseDto;
import com.example.chicken.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private static final String USER_BASE_URL = "/api/users/me";

	private final AuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto request) {
		UserResponseDto result = this.authService.signUp(request);
		return ResponseEntity.created(URI.create(USER_BASE_URL)).body(result);
	}

	@PostMapping("/sign-in")
	public ResponseEntity<AccessTokenResponseDto> signIn(@RequestBody
														 @Valid SignInRequestDto request,
														 HttpServletResponse response) {

		SignInResponseDto tokens = this.authService.signIn(request);

		ResponseCookie cookie = CookieUtil.generateCookieResponse(CookieConstant.REFRESH_TOKEN_NAME,
																  tokens.refreshToken(),
																  Duration.ofDays(15));

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

		AccessTokenResponseDto result = AccessTokenResponseDto.from(tokens.accessToken());

		return ResponseEntity.ok(result);
	}

	@PostMapping("/reissue")
	public ResponseEntity<AccessTokenResponseDto> reissue(@CookieValue(CookieConstant.REFRESH_TOKEN_NAME)
														  @NotBlank
														  @IsJwt String refreshToken) {

		TokenResponseDto tokens = this.authService.reissue(refreshToken);

		ResponseCookie cookie = CookieUtil.generateCookieResponse(CookieConstant.REFRESH_TOKEN_NAME,
																  tokens.refreshToken(),
																  Duration.ofDays(15));

		AccessTokenResponseDto result = AccessTokenResponseDto.from(tokens.accessToken());

		return ResponseEntity
			.status(HttpStatus.OK)
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(result);
	}

	@PostMapping("/logout")
	public ResponseEntity<Object> logout(@CookieValue(CookieConstant.REFRESH_TOKEN_NAME)
										 @NotBlank
										 @IsJwt String refreshToken) {

		this.authService.logout(refreshToken);

		ResponseCookie cookie =
			CookieUtil.generateCookieResponse(CookieConstant.REFRESH_TOKEN_NAME, null, Duration.ofSeconds(0));

		return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.build();
	}

	@PostMapping("/password/request-reset")
	public ResponseEntity<Object> requestPasswordReset(@RequestBody @Valid ResetPasswordRequestDto requestDto) {
		this.authService.requestPasswordReset(requestDto.email());
		return ResponseEntity.noContent().build();
	}

}
