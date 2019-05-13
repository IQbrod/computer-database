package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.exception.UnableToSendErrorCodeException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.model.ServletModel;
import com.excilys.cdb.spring.AppConfig;
import com.excilys.cdb.validator.Validator;

public abstract class Servlet extends HttpServlet {
	private static final long serialVersionUID = 3052019L;
	protected final HashMap<String,ServletModel> modelMap = new HashMap<String,ServletModel>();
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	protected final ConfigurableApplicationContext context;
	
	protected ComputerService computerService;
	protected ComputerMapper computerMapper;
	protected Validator validator;
	
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
	
	protected void sendError(HttpServletResponse response, int code) {
		try {
			response.sendError(500);
		} catch (IOException cause) {
			this.log(new UnableToSendErrorCodeException(code), cause);
		}		
	}
}
