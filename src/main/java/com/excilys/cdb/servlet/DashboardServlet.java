package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servletmodel.*;

public class DashboardServlet extends HttpServlet {	
	private HashMap<String,ServletModel> modelMap = new HashMap<String,ServletModel>();
	
	public DashboardServlet() {
		modelMap.put("pagination", DashboardPagination.getInstance());
		modelMap.put("computerList", DashboardComputerList.getInstance());
	}
	
	private static final long serialVersionUID = 29042019L;
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String page = (request.getParameter("page") == null) ? "1" : request.getParameter("page");
		String size = (request.getParameter("size") == null) ? "10" : request.getParameter("size");
		
		try {
			this.setupDashboard(page,size);
			this.flushSetup(request);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );
	}
	
	private void setupDashboard(String page, String size) throws Exception {
		((DashboardPagination)this.modelMap.get("pagination")).setPage(Integer.valueOf(page));
		((DashboardPagination)this.modelMap.get("pagination")).setSize(Integer.valueOf(size));
		
		int nbComputer = ComputerService.getInstance().count();
		((DashboardComputerList)this.modelMap.get("computerList")).setNbComputer(nbComputer);
		
		int maxPage = nbComputer / Integer.valueOf(size) + ((nbComputer % Integer.valueOf(size) == 0) ? 0 : 1);
		((DashboardPagination)this.modelMap.get("pagination")).setMaxPage(maxPage);
		
		int medPage = Integer.valueOf((Integer.valueOf(page) < 3) ? "3" : ((Integer.valueOf(page) < maxPage-2) ? page : Integer.toString(maxPage-2)));
		((DashboardPagination)this.modelMap.get("pagination")).setMedianPage(medPage);
		
		((DashboardComputerList)this.modelMap.get("computerList")).setList(ComputerService.getInstance().list(page, size));
	}
	
	private void flushSetup(HttpServletRequest request) {
		this.modelMap.values().stream().forEach(x -> x.flush(request));
	}
}
