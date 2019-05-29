package com.excilys.cdb.servlet;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.servlet.model.ServletErrorModel;

@ControllerAdvice
public class CustomExceptionHandler extends ExceptionHandlerExceptionResolver {
	private static final String ADMIN_CONTACT = "Please contact administrator";
	
	
	@ExceptionHandler(value = Exception.class)
	public RedirectView treatException(Exception cause, RedirectAttributes redir) {
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
		
		redir.addFlashAttribute(ErrorServlet.ERROR_PATTERN, error);
		
		cause.printStackTrace();
		return new RedirectView(ErrorServlet.ERROR_PATTERN);
	}
}
