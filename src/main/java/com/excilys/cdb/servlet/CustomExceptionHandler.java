package com.excilys.cdb.servlet;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.servlet.model.ServletErrorModel;

@ControllerAdvice
public class CustomExceptionHandler extends ExceptionHandlerExceptionResolver {
	private static final String ERROR_PATTERN = "error";
	private static final String ADMIN_CONTACT = "Please contact administrator";
	
	
	@ExceptionHandler(value = Exception.class)
	public String treatException(Exception cause, Model model) {
		ServletErrorModel error = new ServletErrorModel(418, ADMIN_CONTACT, cause.getClass().toString());
		
		if (cause instanceof NoHandlerFoundException) {
			error.setErrorCode(404);
			error.setCustomMessage(cause.getMessage());
		} else if (cause instanceof ShouldBeSentToClientException || cause instanceof MethodArgumentTypeMismatchException) {
			error.setErrorCode(400);
			error.setCustomMessage(cause.getMessage());
		} else if (cause instanceof ShouldOnlyBeLoggedException) {
			error.setErrorCode(500);
		} else {
			error.setErrorCode(501);
			error.setCustomMessage("Something went wrong unfortunately: "+cause.getClass().toString());
		}
		
		model.addAttribute(ERROR_PATTERN, error);
		
		return ERROR_PATTERN;
	}
}
