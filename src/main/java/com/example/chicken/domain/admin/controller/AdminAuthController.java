package com.example.chicken.domain.admin.controller;

import static com.example.chicken.common.constant.PathConstant.Admin.ADMIN_AUTH_PREFIX;
import static com.example.chicken.common.constant.PathConstant.Auth.SIGN_IN;

import com.example.chicken.domain.auth.dto.auth.SignInRequestDto;
import com.example.chicken.domain.auth.dto.auth.SignInResponseDto;
import com.example.chicken.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<SignInResponseDto> signInAdmin(@RequestBody @Valid SignInRequestDto request) {

        SignInResponseDto result = this.authService.signInAdmin(request);

        return ResponseEntity.ok(result);
    }

}
