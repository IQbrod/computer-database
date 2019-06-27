package com.excilys.cdb.webservice;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.model.User;
import com.excilys.cdb.service.TokenService;
import com.excilys.cdb.service.UserService;
import com.excilys.cdb.spring.JWTAuthenticationFilter;

@RestController
@RequestMapping(path="/api/auth", produces = "application/json")
@CrossOrigin(origins = "http://localhost:9000")
public class AuthenticationRestController {
	private static final String TOKEN_PREFIX = "Basic ";
	
	private final UserService userService;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
	
	public AuthenticationRestController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public String postLogin(HttpServletRequest request) {
		String header = request.getHeader(JWTAuthenticationFilter.AUTHORIZATION_HEADER);
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			String basic = new String(Base64.getDecoder().decode(header.replace(TOKEN_PREFIX,"")));
			User user = this.userService.findByUsername(
				basic.substring(0,basic.indexOf(":"))
			);
			if (user != null && this.passwordEncoder.matches(basic.substring(basic.indexOf(":")+1), user.getPassword())) {
				return this.tokenService.generateToken(user);
			}
		}
		return null;
	}
}
