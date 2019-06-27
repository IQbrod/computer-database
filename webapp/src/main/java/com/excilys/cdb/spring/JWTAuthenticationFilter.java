package com.excilys.cdb.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.excilys.cdb.mapper.UserMapper;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.TokenService;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	
	private final TokenService tokenService;
	private final UserMapper userMapper;
	
	public JWTAuthenticationFilter(TokenService tokenService, UserMapper userMapper) {
		this.tokenService = tokenService;
		this.userMapper = userMapper;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader(AUTHORIZATION_HEADER);
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			String token = header.replace(TOKEN_PREFIX,"");
			User user = tokenService.getUserFromToken(token);
			if (user != null) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, userMapper.map(user).getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
		SecurityContextHolder.getContext().setAuthentication(null);
	}

}
