package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.servlet.model.editComputer.EditComputerValues;
import com.excilys.cdb.validator.Validator;

public class EditComputerServlet extends Servlet {
	private static final long serialVersionUID = 3052019L;
	
	public EditComputerServlet() {
		this.modelMap.put("values", EditComputerValues.getInstance());
		this.modelMap.put("companyList", SharedCompanyList.getInstance());
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
			throw cons;
		} catch (ShouldBeSentToClientException e) {
			this.log(e);
			throw e;
		} catch (ShouldOnlyBeLoggedException e) {
			this.log(e);
			sendError(response, 500);
		}
	}
	
	protected void setupDashboard(String id) {
		ComputerDto computerDto = ComputerMapper.getInstance().modelToDto( ComputerService.getInstance().read( ComputerMapper.getInstance().idToInt(id) ));
		((EditComputerValues)this.modelMap.get("values")).setComputer(computerDto);
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			ComputerDto computerFromFields = new ComputerDto(request.getParameter("id"),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			Validator.getInstance().validateComputerDto(computerFromFields);
			ComputerService.getInstance().update(ComputerMapper.getInstance().dtoToModel(computerFromFields));
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
