package com.app.ycommerce.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean  //정적 자원(Resource)에 대해서 인증된 사용자가  정적 자원의 접근에 대해 ‘인가’에 대한 설정을 담당하는 메서드
	public WebSecurityCustomizer webSecurityCustomizer() {
		// 정적 자원에 대해서 Security를 적용하지 않음으로 설정
		return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean //HTTP에 대해서 ‘인증’과 ‘인가’를 담당하는 메서드이며 필터를 통해 인증 방식과 인증 절차에 대해서 등록하며 설정을 담당하는 메서드
	public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
		log.debug("[+] WebSecurityConfig Start !!! ");
		//crsf disable : 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
		http
			.csrf((auth) -> auth.disable());
		//From 로그인 방식 disable
		http
			.formLogin((auth) -> auth.disable());
		//http basic 인증 방식 disable
		http
			.httpBasic((auth) -> auth.disable());

		http
			.headers((auth) -> auth.disable());

		//경로별 인가작업
		http
			.authorizeHttpRequests((auth) -> auth
				// .requestMatchers("/login","/","/api/member/signup").permitAll()
				//.requestMatchers()
				.anyRequest().permitAll());
		//세션 설정
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

}
