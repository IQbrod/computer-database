package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.mapper.ServletToDtoComputerMapper;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.servlet.model.editComputer.EditComputerValues;
import com.excilys.cdb.validator.ComputerValidator;

public class EditComputerServlet extends Servlet {
	private static final long serialVersionUID = 3052019L;
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	public EditComputerServlet() {
		this.modelMap.put("values", EditComputerValues.getInstance());
		this.modelMap.put("companyList", SharedCompanyList.getInstance());
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		String id = request.getParameter("id");
		try {
			try {
				
				this.setupDashboard(id);
				this.flushSetup(request);
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward( request, response );
				
			} catch (ServletException | IOException cause) {
				UnexpectedServletException except = new UnexpectedServletException(this.getServletName(),"GET");
				this.logger.error(except.getMessage()+" thrown by "+cause.getMessage(),cause);
				response.sendError(500);
			} catch (InvalidIdException e) {
				this.logger.error(e.getMessage());
				response.sendError(403);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				response.sendError(500);
			}
		} catch (IOException cause) {
			UnableToSendErrorCodeException e = new UnableToSendErrorCodeException(this.getServletName(),"GET");
			this.logger.error(e.getMessage()+" thrown by "+cause.getMessage(),cause);
		}
	}

	protected void setupDashboard(String id) throws Exception {
		ComputerDto computerDto = ComputerService.getInstance().read(id);
		((EditComputerValues)this.modelMap.get("values")).setComputer(computerDto);
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			try {
				
				ComputerDto computerFromFields = new ComputerDto(request.getParameter("id"),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
				ComputerDto computerToUpdate = ServletToDtoComputerMapper.getInstance().convertFields(computerFromFields);
				ComputerValidator.getInstance().validate(computerToUpdate);
				ComputerService.getInstance().update(computerToUpdate);
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
