package com.example.chicken.domain.auth.service;

import com.example.chicken.common.jwt.JwtTokenProvider;
import com.example.chicken.common.jwt.JwtUtil;
import com.example.chicken.domain.auth.dto.auth.SignInRequestDto;
import com.example.chicken.domain.auth.dto.auth.SignInResponseDto;
import com.example.chicken.domain.auth.dto.user.TokenResponseDto;
import com.example.chicken.domain.auth.entity.token.RefreshToken;
import com.example.chicken.domain.auth.entity.token.ResetPasswordToken;
import com.example.chicken.domain.auth.entity.user.User;
import com.example.chicken.domain.auth.exception.PasswordNotMatchedException;
import com.example.chicken.domain.auth.exception.RefreshTokenNotFoundException;
import com.example.chicken.domain.auth.exception.ResetTokenExpiredException;
import com.example.chicken.domain.auth.exception.TokenInvalidException;
import com.example.chicken.domain.auth.exception.UserAlreadyExists;
import com.example.chicken.domain.auth.exception.UserNotFoundException;
import com.example.chicken.domain.auth.repository.RefreshTokenRepository;
import com.example.chicken.domain.auth.repository.ResetPasswordTokenRepository;
import com.example.chicken.domain.auth.repository.UserRepository;
import com.example.chicken.dto.UserRequestDto;
import com.example.chicken.dto.UserResponseDto;
import com.example.chicken.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final EmailService emailService;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final ResetPasswordTokenRepository resetPasswordTokenRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider tokenProvider;
	private final JwtUtil jwtUtil;
	private final TokenMapper tokenMapper;

	@Transactional
	public UserResponseDto signUp(UserRequestDto request) {

		boolean isUserExists = this.userRepository.existsByEmail(request.email());

		if (isUserExists) {
			throw new UserAlreadyExists();
		}

		User userEntity = User.from(request);

		userEntity.updatePassword(passwordEncoder.encode(request.password()));

		User result = this.userRepository.save(userEntity);

		return userMapper.toResponse(result);

	}

	@Transactional
	public SignInResponseDto signIn(SignInRequestDto request) {
		String email = request.email();

		User user = this.userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(email));

		if (!this.passwordEncoder.matches(request.password(), user.getPassword()))
			throw new PasswordNotMatchedException();

		String refreshToken = this.tokenProvider.createRefreshJWT(email);

		this.refreshTokenRepository.save(RefreshToken.of(email, refreshToken));

		String accessToken = this.tokenProvider.createAccessToken(user);

		return new SignInResponseDto(accessToken, refreshToken);
	}

	@Transactional
	public TokenResponseDto reissue(String refreshToken) {
		if (!jwtUtil.validate(refreshToken)) throw new TokenInvalidException();

		String email = jwtUtil.parseClaims(refreshToken).getSubject();

		RefreshToken storedToken = this.refreshTokenRepository.findById(email)
			.orElseThrow(() -> new RefreshTokenNotFoundException(email));

		if (!storedToken.getRefreshJwt().equals(refreshToken)) throw new TokenInvalidException();


		User user = this.userRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(email));

		String newAccessToken = this.tokenProvider.createAccessToken(user);
		String newRefreshToken = this.tokenProvider.createRefreshJWT(email);

		storedToken.renewalToken(newRefreshToken);
		this.refreshTokenRepository.save(storedToken);

		return this.tokenMapper.toDto(newAccessToken, newRefreshToken);
	}

	@Transactional
	public void logout(String refreshToken) {

		String email = jwtUtil.parseClaims(refreshToken).getSubject();

		this.refreshTokenRepository.findById(email).ifPresent(this.refreshTokenRepository::delete);
	}

	@Transactional
	public void requestPasswordReset(String email) {
		boolean isUserExists = this.userRepository.existsByEmail(email);

		if (!isUserExists) {
			throw new UserNotFoundException(email);
		}

		String token = this.tokenProvider.createRefreshJWT(email);

		ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
			.email(email)
			.resetToken(token)
			.build();

		this.resetPasswordTokenRepository.save(resetPasswordToken);

		this.emailService.sendPasswordChangeEmail(email, token);
	}

	public void verifyResetToken(String token) {
		String email = jwtUtil.parseClaims(token).getSubject();

		ResetPasswordToken resetPasswordToken = this.resetPasswordTokenRepository.findById(email)
			.orElseThrow(ResetTokenExpiredException::new);

		if (!resetPasswordToken.getResetToken().equals(token)) {
			throw new TokenInvalidException();
		}
	}

}
