package com.excilys.cdb.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		
		http.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
			
			.antMatchers(HttpMethod.GET, "/", "/computers", "/error").permitAll()
			
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
		auth.inMemoryAuthentication()
			.passwordEncoder(this.passwordEncoder())
			.withUser("user").password(this.passwordEncoder().encode("pass")).roles("USER");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
