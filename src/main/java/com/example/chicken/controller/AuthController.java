package com.example.chicken.controller;

import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String USER_BASE_URL = "/api/users/me";

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto request) {
        UserResponseDto result = this.authService.signUp(request);
        return ResponseEntity.created(URI.create(USER_BASE_URL)).body(result);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn() {
        return ResponseEntity.ok().body(null);
    }

}
