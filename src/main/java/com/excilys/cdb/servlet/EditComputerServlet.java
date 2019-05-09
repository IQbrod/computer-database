package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.mapper.ServletToDtoComputerMapper;
import com.excilys.cdb.servlet.model.SharedCompanyList;
import com.excilys.cdb.servlet.model.editComputer.EditComputerValues;
import com.excilys.cdb.validator.ComputerValidator;

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
			this.log(new UnexpectedServletException(this.getServletName(),"GET"), cause);
			this.sendError(response, 500);
		} catch (Exception e) {
			this.log(e);
			this.sendError(response, 500);
		}
	}
	
	protected void setupDashboard(String id) throws Exception {
		ComputerDto computerDto = ComputerService.getInstance().read(id);
		((EditComputerValues)this.modelMap.get("values")).setComputer(computerDto);
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			ComputerDto computerFromFields = new ComputerDto(request.getParameter("id"),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			ComputerDto computerToUpdate = ServletToDtoComputerMapper.getInstance().convertFields(computerFromFields);
			ComputerValidator.getInstance().validate(computerToUpdate);
			ComputerService.getInstance().update(computerToUpdate);
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
