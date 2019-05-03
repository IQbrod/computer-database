package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidIdException;
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
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		try {
			this.setupDashboard(id);			
		} catch (InvalidIdException e) {
			//TODO: LOG ICI
			response.sendError(403); //Bad Request
		} catch (Exception e) {
			//TODO: LOG ICI
			e.printStackTrace();
		}
		
		
		this.flushSetup(request);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward( request, response );
	}

	protected void setupDashboard(String id) throws Exception {
		ComputerDto computerDto = ComputerService.getInstance().read(id);
		((EditComputerValues)this.modelMap.get("values")).setComputer(computerDto);
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		try {
			ComputerDto computerFromFields = new ComputerDto(request.getParameter("id"),request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			ComputerDto computerToUpdate = ServletToDtoComputerMapper.getInstance().convertFields(computerFromFields);
			ComputerValidator.getInstance().validate(computerToUpdate);
			ComputerService.getInstance().update(computerToUpdate);
		} catch (Exception e) {
			// TODO: LOG ICI
			e.printStackTrace();
		}
		response.sendRedirect(this.getServletContext().getContextPath()+"/");
	}
}
