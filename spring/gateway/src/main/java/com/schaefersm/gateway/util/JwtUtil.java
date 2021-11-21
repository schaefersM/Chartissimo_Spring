package com.schaefersm.gateway.util;

import com.schaefersm.gateway.exception.JwtTokenMalformedException;
import com.schaefersm.gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	@Value("${secret.access}")
	private String accessSecret;

	@Value("${secret.refresh}")
	private String refreshSecret;


	public void validateAccessToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		validateToken(token, accessSecret);
	}

	public void validateRefreshToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		validateToken(token, refreshSecret);
	}

	private void validateToken(String token, String secret) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}

	}

}