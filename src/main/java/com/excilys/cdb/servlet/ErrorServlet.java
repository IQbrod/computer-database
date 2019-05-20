package com.excilys.cdb.servlet;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.servlet.model.ServletErrorModel;

@ControllerAdvice
public class ErrorServlet extends ExceptionHandlerExceptionResolver {
	private static final String ERROR_PATTERN = "error";
	private static final String ADMIN_CONTACT = "Please contact administrator";
	
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView treatException(Exception cause) {
		
		ServletErrorModel error = new ServletErrorModel(418, "I'm a teapot", ADMIN_CONTACT);
		
		if (cause instanceof NoHandlerFoundException) {
			error.setErrorCode(404);
			error.setMessage("Not Found");
			error.setCustomMessage(cause.getMessage());
		} else if (cause instanceof ShouldBeSentToClientException || cause instanceof MethodArgumentTypeMismatchException) {
			error.setErrorCode(400);
			error.setMessage("Bad Request");
			error.setCustomMessage(cause.getMessage());
		} else if (cause instanceof ShouldOnlyBeLoggedException) {
			error.setErrorCode(500);
			error.setMessage("Internal Server Error");
		} else {
			error.setErrorCode(501);
			error.setMessage("Not Implemented");
			error.setCustomMessage("Something went wrong unfortunately: "+cause.getClass().toString());
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(ERROR_PATTERN, error);
		mav.setViewName(ERROR_PATTERN);
		
		return mav;
	}
}
