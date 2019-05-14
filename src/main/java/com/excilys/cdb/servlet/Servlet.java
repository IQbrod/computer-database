package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.exception.ShouldBeSentToClientException;
import com.excilys.cdb.exception.ShouldOnlyBeLoggedException;
import com.excilys.cdb.exception.UnableToSendErrorCodeException;
import com.excilys.cdb.exception.UnexpectedServletException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.model.ServletModel;
import com.excilys.cdb.spring.AppConfig;
import com.excilys.cdb.validator.Validator;

public abstract class Servlet extends HttpServlet {
	private static final String ERROR_ADMIN_CALL = "Please contact administrator";
	private static final long serialVersionUID = 3052019L;
	protected final HashMap<String,ServletModel> modelMap = new HashMap<>();
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	protected final ConfigurableApplicationContext context;
	
	protected final ComputerService computerService;
	protected final ComputerMapper computerMapper;
	protected final Validator validator;
	
	protected Servlet() {
		this.context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		this.computerService = context.getBean(ComputerService.class);
		this.computerMapper = context.getBean(ComputerMapper.class);
		this.validator = context.getBean(Validator.class);
	}
	
	protected void flushSetup(HttpServletRequest request) {
		this.modelMap.values().stream().forEach(x -> x.flush(request));
	}
	
	protected void log(Exception ex) {
		this.logger.error(ex.getMessage());
	}
	
	protected void log(Exception ex, Exception cause) {
		this.logger.error(ex.getMessage()+" caused by "+ cause.getMessage(), cause);
	}
	
	protected void sendError(HttpServletResponse response, int code, String message) {
		try {
			if (message == null)
				response.sendError(code);
			else
				response.sendError(code, message);
		} catch (IOException cause) {
			this.log(new UnableToSendErrorCodeException(code), cause);
		}		
	}
	
	protected void treatException(Exception cause, HttpServletResponse response) {
		if (cause instanceof ServletException || cause instanceof IOException) {
			UnexpectedServletException cons = new UnexpectedServletException(this.getServletName(),"GET");
			this.log(cons, cause);
			sendError(response, 500, ERROR_ADMIN_CALL);
		} else if (cause instanceof ShouldBeSentToClientException) {
			this.log(cause);
			sendError(response, 400, cause.getMessage());
		} else if (cause instanceof ShouldOnlyBeLoggedException) {
			this.log(cause);
			sendError(response, 500, ERROR_ADMIN_CALL);
		} else {
			sendError(response, 501, null);
		}
	}
}
