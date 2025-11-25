package com.example.chicken.config;

import com.example.chicken.common.dto.ExceptionResponseDto;
import com.example.chicken.common.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends GenericFilterBean {

	private static final String BEARER_PREFIX = "Bearer";
	private final JwtUtil jwtUtil;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {

		try {
			String token = resolveToken((HttpServletRequest)servletRequest);

			if (token != null && jwtUtil.validate(token))
				SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));

			filterChain.doFilter(servletRequest, servletResponse);

		} catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | SignatureException |
				 IllegalArgumentException e) {
			setErrorResponse((HttpServletResponse)servletResponse, e.getMessage());
		}

	}

	private void setErrorResponse(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(ContentType.APPLICATION_JSON.getMimeType());

		ExceptionResponseDto responseDto = new ExceptionResponseDto(HttpStatus.UNAUTHORIZED.value(), message, null);

		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(responseDto));
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX))
			return bearerToken.substring(7);

		return null;
	}

}
