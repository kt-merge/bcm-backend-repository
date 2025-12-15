package com.example.chicken.domain.auth.controller;

import static com.example.chicken.common.constant.PathConstant.Auth.*;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chicken.common.constant.CookieConstant;
import com.example.chicken.common.jwt.annotation.IsJwt;
import com.example.chicken.common.util.CookieUtil;
import com.example.chicken.domain.auth.dto.RequestResetPasswordDto;
import com.example.chicken.domain.auth.dto.ResetPasswordDto;
import com.example.chicken.domain.auth.dto.auth.SignInRequestDto;
import com.example.chicken.domain.auth.dto.auth.SignInResponseDto;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.domain.auth.dto.user.AccessTokenResponseDto;
import com.example.chicken.domain.auth.dto.user.TokenResponseDto;
import com.example.chicken.domain.auth.service.AuthService;
import com.example.chicken.domain.auth.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(AUTH_PREFIX)
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final UserService userService;

	@PostMapping(SIGN_UP)
	public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto request) {

		UserResponseDto result = this.authService.signUp(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PostMapping(SIGN_IN)
	public ResponseEntity<AccessTokenResponseDto> signIn(@RequestBody @Valid SignInRequestDto request) {

		SignInResponseDto tokens = this.authService.signIn(request);

		Duration cookieDuration = (request.rememberMe() == null || !Boolean.TRUE.equals(request.rememberMe())) ?
			Duration.ofSeconds(-1) : Duration.ofDays(15);

		ResponseCookie cookie = CookieUtil.generateCookieResponse(CookieConstant.REFRESH_TOKEN_NAME,
																  tokens.refreshToken(),
																  cookieDuration);

		AccessTokenResponseDto result = AccessTokenResponseDto.from(tokens.accessToken());

		return ResponseEntity
			.status(HttpStatus.OK)
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(result);
	}

	@PostMapping(REISSUE)
	public ResponseEntity<AccessTokenResponseDto> reissue(@CookieValue(CookieConstant.REFRESH_TOKEN_NAME)
														  @NotBlank @IsJwt String refreshToken) {

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

	@PostMapping(LOGOUT)
	public ResponseEntity<Void> logout(@CookieValue(CookieConstant.REFRESH_TOKEN_NAME)
									   @NotBlank @IsJwt String refreshToken) {

		this.authService.logout(refreshToken);

		ResponseCookie cookie =
			CookieUtil.generateCookieResponse(CookieConstant.REFRESH_TOKEN_NAME, null, Duration.ofSeconds(0));

		return ResponseEntity.noContent()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.build();
	}

	@PostMapping(Password.REQUEST_RESET)
	public ResponseEntity<Void> requestPasswordReset(@RequestBody @Valid RequestResetPasswordDto requestDto) {

		this.authService.requestPasswordReset(requestDto.email());

		return ResponseEntity.noContent().build();
	}

	@GetMapping(Password.VERIFY)
	public ResponseEntity<Boolean> verifyResetToken(@RequestParam @NotBlank String token) {

		this.authService.verifyResetToken(token);

		return ResponseEntity.ok().body(true);
	}

	@PostMapping(Password.RESET)
	public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordDto request) {

		this.userService.updatePassword(request.password(), request.resetToken());

		return ResponseEntity.noContent().build();
	}

}
