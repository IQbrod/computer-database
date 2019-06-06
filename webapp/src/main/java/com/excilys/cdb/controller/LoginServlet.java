package com.excilys.cdb.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.mapper.UserDtoMapper;
import com.excilys.cdb.service.UserService;
import com.excilys.cdb.validator.Validator;

@Controller
public class LoginServlet {
	private final Validator validator;
	private final UserDtoMapper mapper;
	private final UserService service;
	
	public LoginServlet(Validator validator, UserDtoMapper mapper, UserService service) {
		this.validator = validator;
		this.mapper = mapper;
		this.service = service;
	}
	
	@ModelAttribute("user")
	public UserDto computerAttribute() {
		return new UserDto(0L);
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String getLogout() {
		return "logout";
	}
	
	@GetMapping("/signup")
	public String getSignup() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public RedirectView postSignup(@Valid @ModelAttribute("user") UserDto user) {
		this.validator.validateUserDto(user);
		this.service.create(this.mapper.dtoToModel(user));
		return new RedirectView("computers");
	}
}
