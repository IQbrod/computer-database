package com.excilys.cdb.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.excilys.cdb.mapper.UserMapper;
import com.excilys.cdb.service.TokenService;
import com.excilys.cdb.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userService;
	private final TokenService tokenService;
    private final UserMapper userMapper;
	
	public SpringSecurityConfig(UserService userService, TokenService tokenService, UserMapper userMapper) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.userMapper = userMapper;
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl role = new RoleHierarchyImpl();
		role.setHierarchy("ROLE_ADMIN > ROLE_CUSTOM > ROLE_USER");
		return role;
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
//		
//		http.authorizeRequests()
//			.mvcMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
//			
//			.mvcMatchers(HttpMethod.GET, "/", "/computers", "/error", "/signup").permitAll()
//			.mvcMatchers(HttpMethod.POST, "/signup").permitAll()
//			
//			.mvcMatchers("/api/**").permitAll()
//			
//			.mvcMatchers(HttpMethod.GET, "/computers/new").hasRole("USER")
//			.mvcMatchers(HttpMethod.POST, "/computers").hasRole("USER")
//			
//			.mvcMatchers(HttpMethod.GET, "/computers/**").hasRole("CUSTOM")
//			.mvcMatchers(HttpMethod.PUT, "/computers").hasRole("CUSTOM")
//			
//			.anyRequest().hasRole("ADMIN")
//		.and().formLogin()
//			.loginPage("/login").permitAll()
//			.loginProcessingUrl("/login")
//			.usernameParameter("username")
//			.passwordParameter("password")
//		.and().logout()
//			.logoutUrl("/logout").permitAll()
//			.logoutSuccessUrl("/computers")
//		.and().csrf().disable();
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
		http.authorizeRequests()
			.anyRequest().permitAll()
		.and()
		.csrf().disable()
		.formLogin().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(tokenService, userMapper);
	}
}
