package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.UnableToSendErrorCodeException;
import com.excilys.cdb.exception.UnexpectedServletException;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.mapper.ServletToDtoComputerMapper;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.validator.ComputerValidator;

public class AddComputerServlet extends Servlet {
	private static final long serialVersionUID = 29042019L;
	private final Logger logger = LogManager.getLogger(this.getClass());	
	
	public AddComputerServlet() {
		this.modelMap.put("companyList", SharedCompanyList.getInstance());
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			try {
				
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
				this.flushSetup(request);
				
			} catch (ServletException | IOException cause) {
				UnexpectedServletException except = new UnexpectedServletException(this.getServletName(),"GET");
				this.logger.error(except.getMessage()+" thrown by "+cause.getMessage(),cause);
				response.sendError(500);
			}
		} catch (IOException cause) {
			UnableToSendErrorCodeException e = new UnableToSendErrorCodeException(this.getServletName(),"GET");
			this.logger.error(e.getMessage()+" thrown by "+cause.getMessage(),cause);
		}
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			try {
				
				ComputerDto computerFromFields = new ComputerDto("0",request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
				ComputerDto computerToCreate = ServletToDtoComputerMapper.getInstance().convertFields(computerFromFields);
				ComputerValidator.getInstance().validate(computerToCreate);
				ComputerService.getInstance().create(computerToCreate);
				response.sendRedirect(this.getServletContext().getContextPath()+"/");
				
			} catch (ServletException | IOException cause) {
				UnexpectedServletException except = new UnexpectedServletException(this.getServletName(),"POST");
				this.logger.error(except.getMessage()+" thrown by "+cause.getMessage(),cause);
				response.sendError(500);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				response.sendError(500);
			}
		} catch (IOException cause) {
			UnableToSendErrorCodeException e = new UnableToSendErrorCodeException(this.getServletName(),"POST");
			this.logger.error(e.getMessage()+" thrown by "+cause.getMessage(),cause);
		}
	}
}
