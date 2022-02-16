package br.com.gx.socialNetwork.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.gx.socialNetwork.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${socialNetwork.jwt.expiration}")
	private String expiration;

	@Value("${socialNetwork.jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {
		User logged = (User) authentication.getPrincipal();
		Date today = new Date();
		Date dataExpiracao = new Date(today.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer("Social Network").setSubject(logged.getId().toString()).setIssuedAt(today)
				.setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
