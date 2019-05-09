package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.UnexpectedServletException;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.mapper.ServletToDtoComputerMapper;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.validator.ComputerValidator;

public class AddComputerServlet extends Servlet {
	private static final long serialVersionUID = 29042019L;	
	
	public AddComputerServlet() {
		this.modelMap.put("companyList", SharedCompanyList.getInstance());
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
			this.flushSetup(request);
			
		} catch (ServletException | IOException cause) {
			this.log(new UnexpectedServletException(this.getServletName(),"GET"), cause);
			this.sendError(response, 500);
		}
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			ComputerDto computerFromFields = new ComputerDto("0",request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			ComputerDto computerToCreate = ServletToDtoComputerMapper.getInstance().convertFields(computerFromFields);
			ComputerValidator.getInstance().validate(computerToCreate);
			ComputerService.getInstance().create(computerToCreate);
			response.sendRedirect(this.getServletContext().getContextPath()+"/");
			
		} catch (ServletException | IOException cause) {
			this.log(new UnexpectedServletException(this.getServletName(),"POST"), cause);
			this.sendError(response, 500);
		} catch (Exception e) {
			this.log(e);
			this.sendError(response, 500);
		}
	}
}
