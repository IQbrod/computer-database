package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.ShouldBeSentToClientException;
import com.excilys.cdb.exception.ShouldOnlyBeLoggedException;
import com.excilys.cdb.exception.UnexpectedServletException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.validator.Validator;

public class AddComputerServlet extends Servlet {
	private static final long serialVersionUID = 29042019L;	
	
	public AddComputerServlet() {
		this.modelMap.put("companyList", SharedCompanyList.getInstance());
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			this.flushSetup(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
			
		} catch (ServletException | IOException cause) {
			UnexpectedServletException cons = new UnexpectedServletException(this.getServletName(),"GET");
			this.log(cons, cause);
			throw cons;
		} catch (ShouldBeSentToClientException e) {
			this.log(e);
			throw e;
		} catch (ShouldOnlyBeLoggedException e) {
			this.log(e);
			sendError(response, 500);
		}
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			ComputerDto computerFromFields = new ComputerDto("0",request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			Validator.getInstance().validateComputerDto(computerFromFields);
			ComputerService.getInstance().create(ComputerMapper.getInstance().dtoToModel(computerFromFields));
			response.sendRedirect(this.getServletContext().getContextPath()+"/");
			
		} catch (IOException cause) {
			UnexpectedServletException cons = new UnexpectedServletException(this.getServletName(),"POST");
			this.log(cons, cause);
			throw cons;
		} catch (ShouldBeSentToClientException e) {
			this.log(e);
			throw e;
		} catch (ShouldOnlyBeLoggedException e) {
			this.log(e);
			sendError(response, 500);
		}
	}
}
