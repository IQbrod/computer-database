package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.excilys.cdb.service.ComputerService;

public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 29042019L;
	
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String page = (request.getParameter("page") == null) ? "1" : request.getParameter("page");
		String size = (request.getParameter("size") == null) ? "10" : request.getParameter("size");
		
		try {
			int nbComputer = this.setComputerNumber(request);
			this.setComputerList(request, page, size);
			this.setPagination(request, page, size, nbComputer);
			
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );
	}
	
	private int setComputerNumber(HttpServletRequest request) throws Exception {
		int nbComputer = ComputerService.getInstance().listAllElements().size();
		request.setAttribute("numberOfComputers", nbComputer);
		return nbComputer;
	}
	
	private void setComputerList(HttpServletRequest request, String page, String size) throws Exception {
		request.setAttribute("computerList", ComputerService.getInstance().list(page, size));
	}
	
	private void setPagination(HttpServletRequest request, String page, String size, int nbComputer) {
  		int maxPage = nbComputer / Integer.valueOf(size) + ((nbComputer % Integer.valueOf(size) == 0) ? 0 : 1);
  		int medPage = Integer.valueOf((Integer.valueOf(page) < 3) ? "3" : ((Integer.valueOf(page) < maxPage-2) ? page : Integer.toString(maxPage-2)));
  		
  		request.setAttribute("maxPage", maxPage);
  		request.setAttribute("medianPage", medPage);
	}
}
