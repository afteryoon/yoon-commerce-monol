package com.app.ycommerce.utils.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.ycommerce.dto.CustomUserDetails;
import com.app.ycommerce.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	private final JWTUtil jwtUtil;

	public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil
	) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			Member member = new ObjectMapper().readValue(request.getInputStream(), Member.class);
			String username = member.getEmail();
			String password = member.getPassword();

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
				null);

			return authenticationManager.authenticate(authToken);

		} catch (IOException e) {
			throw new RuntimeException("Could not read request body", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {

		CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();

		String username = customUserDetails.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();

		String role = auth.getAuthority();

		String token = jwtUtil.createJwt(username, role);
		//Http식 인증 방식은 RFC 7235 정의에 따라 인증 헤더의 형태를 가져야 함
		response.addHeader("Authorization", "Bearer " + token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authenticationException) throws IOException, ServletException {

		response.setStatus(401);
	}
}
