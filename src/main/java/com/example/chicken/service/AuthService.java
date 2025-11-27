package com.example.chicken.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chicken.common.jwt.JwtTokenProvider;
import com.example.chicken.common.jwt.JwtUtil;
import com.example.chicken.domain.Role;
import com.example.chicken.domain.User;
import com.example.chicken.domain.auth.RefreshToken;
import com.example.chicken.dto.SignInRequestDto;
import com.example.chicken.dto.SignInResponseDto;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.dto.user.TokenResponseDto;
import com.example.chicken.repository.RefreshTokenRepository;
import com.example.chicken.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider tokenProvider;
	private final JwtUtil jwtUtil;

	@Transactional
	public UserResponseDto signUp(UserRequestDto request) {
		if (this.userRepository.findByEmail(request.email()).isPresent())
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");

		User userEntity = User.from(request);

		userEntity.encodePassword(passwordEncoder.encode(request.password()));

		User result = this.userRepository.save(userEntity);

		return UserResponseDto.from(result);
	}

	@Transactional
	public SignInResponseDto signIn(SignInRequestDto request) {
		User user = this.userRepository.findByEmail(request.email()).orElseThrow(IllegalArgumentException::new);

		if (!this.passwordEncoder.matches(request.password(), user.getPassword()))
			throw new IllegalArgumentException();

		String refreshToken = this.tokenProvider.createRefreshJWT(request.email());

		RefreshToken tokenEntity = RefreshToken.builder()
			.email(request.email())
			.refreshToken(refreshToken)
			.build();

		this.refreshTokenRepository.save(tokenEntity);

		String accessToken = this.tokenProvider.createJWT(user.getEmail(), user.getNickname(), Role.USER);

		return new SignInResponseDto(accessToken, refreshToken);
	}

	@Transactional
	public TokenResponseDto reissue(String refreshToken) {

		if (!jwtUtil.validate(refreshToken))
			throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");

		String email = jwtUtil.parseClaims(refreshToken).getSubject();

		User user = this.userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);

		RefreshToken savedToken = this.refreshTokenRepository.findById(email)
			.orElseThrow(() -> new IllegalArgumentException("리프레시 토큰이 존재하지 않습니다."));

		if (!savedToken.getRefreshJwt().equals(refreshToken))
			throw new IllegalArgumentException("리프레시 토큰이 일치하지 않습니다.");

		String refreshJWT = this.tokenProvider.createRefreshJWT(email);

		savedToken.renewalToken(refreshJWT);

		this.refreshTokenRepository.save(savedToken);

		return this.tokenProvider.createTokens(user.getEmail(), user.getPassword());
	}

	@Transactional
	public void logout(String refreshToken) {

		String email = jwtUtil.parseClaims(refreshToken).getSubject();

		this.refreshTokenRepository.findById(email)
			.ifPresent(this.refreshTokenRepository::delete);
	}

}
