package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class AddComputer extends HttpServlet {

	private static final long serialVersionUID = 29042019L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		try {
			this.setCompanyIdList(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward( request, response );
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		System.err.print(request.getParameter("computerName")+",");
		System.err.print(request.getParameter("introduced")+",");
		System.err.print(request.getParameter("discontinued")+",");
		System.err.print(request.getParameter("companyId"));
		
		try {
			ComputerService.getInstance().create(new ComputerDto(
					"0",
					request.getParameter("computerName"),
					(request.getParameter("introduced").equals("")) ? null : request.getParameter("introduced"),
					(request.getParameter("discontinued").equals("")) ? null : request.getParameter("discontinued"),
					new CompanyDto(request.getParameter("companyId"),"None")
			));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		response.sendRedirect(this.getServletContext().getContextPath()+"/");
	}
	
	private void setCompanyIdList(HttpServletRequest request) throws Exception {
		request.setAttribute("companyList", CompanyService.getInstance().listAllElements());
	}
}
