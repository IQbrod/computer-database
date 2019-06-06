package com.excilys.cdb.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.cdb.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserService userService;
	
	public SpringSecurityConfig(UserService userService) {
		this.userService = userService;
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl role = new RoleHierarchyImpl();
		role.setHierarchy("ROLE_ADMIN > ROLE_USER");
		return role;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		
		http.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
			
			.antMatchers(HttpMethod.GET, "/", "/computers", "/error", "/signup").permitAll()
			.antMatchers(HttpMethod.POST, "/signup").permitAll()
			
			.antMatchers(HttpMethod.GET, "/computers/new").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/computers").hasRole("USER")
			
			.anyRequest().hasRole("ADMIN")
		.and().formLogin()
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/login")
			.usernameParameter("username")
			.passwordParameter("password")
		.and().logout()
			.logoutUrl("/logout").permitAll()
			.logoutSuccessUrl("/computers")
		.and().csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
