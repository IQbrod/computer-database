package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.exception.UnableToSendErrorCodeException;
import com.excilys.cdb.servlet.model.ServletModel;

public abstract class Servlet extends HttpServlet {
	private static final long serialVersionUID = 3052019L;
	protected final HashMap<String,ServletModel> modelMap = new HashMap<String,ServletModel>();
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
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
