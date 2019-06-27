package com.excilys.cdb.service;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	private static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final UserService userService;
	
	public TokenService(UserService userService) {
		this.userService = userService;
	}
	
	public User getUserFromToken(String token) {
		return userService.findByUsername(
			Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody()
            .getSubject()
		);
	}
	
	public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SECRET)
                .compact();
	}
}
