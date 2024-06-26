package com.app.ycommerce.utils.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.ycommerce.dto.CustomUserDetails;
import com.app.ycommerce.entity.Member;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;

	public JWTFilter(JWTUtil jwtUtil) {

		this.jwtUtil = jwtUtil;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		//request 에서 Authorization 헤더 확인
		String authorization = request.getHeader("Authorization");
		//Authorization 헤더 값 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			System.out.println("token null");
			filterChain.doFilter(request, response);
			//메소드 종료 (값 검증 실패시)
			return;
		}

		String token = authorization.split(" ")[1];
		//토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {

			System.out.println("token expired");
			filterChain.doFilter(request, response);

			//만료시 종료
			return;
		}

		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);

		Member member = Member.builder()
			.email(username)
			.password("temppwd")
			.role(role)
			.build();

		//UserDetails에 회원 정보 객체 담기
		CustomUserDetails customUserDetails = new CustomUserDetails(member);

		//스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
			customUserDetails.getAuthorities());
		//세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
