package br.com.maddytec.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.maddytec.constants.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

	public String createToken(String email, List<String> roles) {
		LocalDateTime localDateTime = LocalDateTime.now().plusDays(SecurityConstant.JWT_EXP_DAYS);

		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		String jwt = Jwts.builder()
				.setSubject(email)
				.setExpiration(date)
				.claim(SecurityConstant.JWT_ROLE_KEY, roles)
				.signWith(SignatureAlgorithm.HS512, SecurityConstant.API_KEY.getBytes())
				.compact();

		return jwt;
	}

	public Claims parseToken(String jwt) throws JwtException {
		Claims claims = Jwts.parser()
				.setSigningKey(SecurityConstant.API_KEY.getBytes())
				.parseClaimsJws(jwt)
				.getBody();

		return claims;
	}
}
