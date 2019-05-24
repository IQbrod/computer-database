package com.excilys.cdb.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.excilys.cdb.servlet.model.ServletErrorModel;

@Controller
public class ErrorServlet {
	protected static final String ERROR_PATTERN = "error";
	
	@GetMapping(ERROR_PATTERN)
	public String displayError(@ModelAttribute("error") ServletErrorModel error) {
		return ERROR_PATTERN;
	}
	
}
