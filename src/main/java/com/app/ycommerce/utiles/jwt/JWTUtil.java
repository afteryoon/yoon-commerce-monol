package com.app.ycommerce.utiles.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

	private SecretKey secretKey;
	private static final long JWT_EXPIRATION_MS = 86400000; //24 시간 1일

	public JWTUtil(@Value("${jwt.secret}") String secret) {

		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	//토큰 검증
	public String getUsername(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("username", String.class);
	}

	public String getRole(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

	public Boolean isExpired(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

	//토큰 생성
	public String createJwt(String username, String role) {
		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder()
			.claim("username", username)
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis())) //현재 발행시간
			.expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS)) //소멸시간
			.signWith(secretKey)
			.compact();
	}

}
