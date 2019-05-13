package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.servlet.model.editComputer.EditComputerValues;

public class EditComputerServlet extends Servlet {
	private static final long serialVersionUID = 3052019L;
	
	public EditComputerServlet() {
		super();
		
		this.modelMap.put("values", context.getBean(EditComputerValues.class));
		this.modelMap.put("companyList", context.getBean(SharedCompanyList.class));
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		String id = request.getParameter("id");
		
		try {
			
			this.setupDashboard(id);
			this.flushSetup(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward( request, response );
			
		} catch (ServletException | IOException cause) {
			UnexpectedServletException cons = new UnexpectedServletException(this.getServletName(),"GET");
			this.log(cons, cause);
			sendError(response, 500);
		} catch (ShouldBeSentToClientException e) {
			this.log(e);
			sendError(response, 400);
		} catch (ShouldOnlyBeLoggedException e) {
			this.log(e);
			sendError(response, 500);
		}
	}
	
	protected void setupDashboard(String id) {
		ComputerDto computerDto = this.computerMapper.modelToDto( this.computerService.read( this.computerMapper.idToInt(id) ));
		((EditComputerValues)this.modelMap.get("values")).setComputer(computerDto);
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			ComputerDto computerFromFields = new ComputerDto(request.getParameter("id"),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			this.validator.validateComputerDto(computerFromFields);
			this.computerService.update(this.computerMapper.dtoToModel(computerFromFields));
			response.sendRedirect(this.getServletContext().getContextPath()+"/");
			
		} catch (IOException cause) {
			UnexpectedServletException cons = new UnexpectedServletException(this.getServletName(),"POST");
			this.log(cons, cause);
			sendError(response, 500);
		} catch (ShouldBeSentToClientException e) {
			this.log(e);
			sendError(response, 400);
		} catch (ShouldOnlyBeLoggedException e) {
			this.log(e);
			sendError(response, 500);
		}
	}
}
