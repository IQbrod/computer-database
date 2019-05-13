package com.excilys.cdb.servlet;

import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.servlet.model.SharedCompanyList;

public class AddComputerServlet extends Servlet {
	private static final long serialVersionUID = 29042019L;	
	
	public AddComputerServlet() {
		super();
		this.modelMap.put("companyList", context.getBean(SharedCompanyList.class));
	}

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			this.flushSetup(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
			
		} catch (Exception e) {
			this.treatException(e, response);
		}
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		try {
			
			ComputerDto computerFromFields = new ComputerDto("0",request.getParameter("computerName"),request.getParameter("introduced"),request.getParameter("discontinued"),request.getParameter("companyId"),"None");
			this.validator.validateComputerDto(computerFromFields);
			this.computerService.create(this.computerMapper.dtoToModel(computerFromFields));
			response.sendRedirect(this.getServletContext().getContextPath()+"/");
			
		} catch (Exception e) {
			this.treatException(e, response);
		}
	}
}
