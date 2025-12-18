package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_AUTH_PREFIX;
import static com.example.chicken.common.constant.PathConstant.Auth.SIGN_IN;

import com.example.chicken.common.constant.CookieConstant;
import com.example.chicken.common.util.CookieUtil;
import com.example.chicken.domain.auth.dto.auth.SignInRequestDto;
import com.example.chicken.domain.auth.dto.auth.SignInResponseDto;
import com.example.chicken.domain.auth.dto.user.AccessTokenResponseDto;
import com.example.chicken.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_AUTH_PREFIX)
public class AdminAuthController {

    private final AuthService authService;

    @PostMapping(SIGN_IN)
    public ResponseEntity<AccessTokenResponseDto> signInAdmin(@RequestBody @Valid SignInRequestDto request) {

        SignInResponseDto tokens = this.authService.signInAdmin(request);

        Duration cookieDuration = Boolean.TRUE.equals(request.rememberMe())
                ? Duration.ofDays(15) : Duration.ofSeconds(-1);

        ResponseCookie cookie = CookieUtil.generateCookieResponse(CookieConstant.REFRESH_TOKEN_NAME,
                tokens.refreshToken(),
                cookieDuration);

        AccessTokenResponseDto result = AccessTokenResponseDto.from(tokens.accessToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result);
    }

}
