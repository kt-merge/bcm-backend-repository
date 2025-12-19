package com.example.chicken.domain.auth.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.chicken.common.jwt.JwtTokenProvider;
import com.example.chicken.domain.admin.exception.RoleNotAllowedException;
import com.example.chicken.domain.auth.dto.auth.SignInRequestDto;
import com.example.chicken.domain.auth.dto.auth.SignInResponseDto;
import com.example.chicken.domain.auth.entity.token.RefreshToken;
import com.example.chicken.domain.auth.entity.user.Role;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("관리자 로그인")
    void signInAdmin_success() {
        //given
        String email = "admin@chicken.com";
        String password = "password1234";
        Boolean rememberMe = true;

        SignInRequestDto requestDto = new SignInRequestDto(email, password, rememberMe, Role.ADMIN);

        String encodedPassword = "encodedPassword";
        User adminUser = User.builder()
                .email(email)
                .password(encodedPassword)
                .role(Role.ADMIN)
                .build();

        given(this.userQueryService.getUserByEmail(email)).willReturn(adminUser);
        given(this.passwordEncoder.matches(password, encodedPassword)).willReturn(true);

        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        given(this.tokenProvider.createAccessToken(any(User.class))).willReturn(accessToken);
        given(this.tokenProvider.createRefreshJWT(any(String.class))).willReturn(refreshToken);
        given(this.refreshTokenRepository.save(any(RefreshToken.class)))
                .willReturn(RefreshToken.of(email, refreshToken));
        //when
        SignInResponseDto responseDto = this.authService.signIn(requestDto);

        //then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.accessToken()).isEqualTo(accessToken);
        assertThat(responseDto.refreshToken()).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("관리자 로그인 - 권한 없음")
    void signInAdmin_role_fail() {
        String email = "admin@chicken.com";
        String password = "password1234";
        Boolean rememberMe = true;

        SignInRequestDto requestDto = new SignInRequestDto(email, password, rememberMe, Role.ADMIN);

        User normalUser = User.builder()
                .email(email)
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        given(this.userQueryService.getUserByEmail(email)).willReturn(normalUser);

        assertThrows(RoleNotAllowedException.class, () -> this.authService.signIn(requestDto));
    }

}
