package com.excilys.cdb.servlet;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.*;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.servlet.model.dashboard.DashboardComputerList;
import com.excilys.cdb.servlet.model.dashboard.DashboardPagination;

public class DashboardServlet extends Servlet {	
	private static final long serialVersionUID = 3052019L;
	private static final String PAGINATION = "pagination";
	private static final String COMPUTER_LIST = "computerList";
	private static final String PAGE = "page";
	private static final String SIZE = "size";
	private static final String ORDERBY = "orderBy";
	private static final String SEARCH = "search";
	
	
	public DashboardServlet() {	
		super();
		
		this.modelMap.put(PAGINATION, context.getBean(DashboardPagination.class));
		this.modelMap.put(COMPUTER_LIST, context.getBean(DashboardComputerList.class));
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {	
		try {
			
			this.setupDashboard(request);
			this.flushSetup(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );
			
		} catch (Exception e) {
			this.treatException(e, response);
		}
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		String[] listId = request.getParameter("selection").split(",");
		
		try {
			
			for (String id : listId) {
				this.computerService.delete(this.computerMapper.dtoToModel(new ComputerDto(id)));
			}
			response.sendRedirect(this.getServletContext().getContextPath()+"/?"+PAGE+"="+ ((DashboardPagination)this.modelMap.get(PAGINATION)).getPage() +"&"+SIZE+"="+ ((DashboardPagination)this.modelMap.get(PAGINATION)).getSize());
			
		} catch (Exception e) {
			this.treatException(e, response);
		}
	}

	private void setupDashboard(HttpServletRequest request) {
		if (request.getParameter(PAGE) == null && request.getParameter(SIZE) == null && request.getParameter(PAGE) == null) {
			((DashboardPagination)this.modelMap.get(PAGINATION)).setDefault();
		}
			
		if (request.getParameter(PAGE) != null) {
			this.validator.validatePagination(request.getParameter(PAGE));
			((DashboardPagination)this.modelMap.get(PAGINATION)).setPage(Integer.valueOf(request.getParameter(PAGE)));
		}
		if (request.getParameter(SIZE) != null) {
			this.validator.validatePagination(request.getParameter(SIZE));
			((DashboardPagination)this.modelMap.get(PAGINATION)).setSize(Integer.valueOf(request.getParameter(SIZE)));
		}
		if (request.getParameter(SEARCH) != null)
			((DashboardPagination)this.modelMap.get(PAGINATION)).setSearch(request.getParameter(SEARCH));
		if (request.getParameter(ORDERBY) != null)
			((DashboardPagination)this.modelMap.get(PAGINATION)).setOrderBy(request.getParameter(ORDERBY));
		
		int page = ((DashboardPagination)this.modelMap.get(PAGINATION)).getPage();
		int size = ((DashboardPagination)this.modelMap.get(PAGINATION)).getSize();
		String search = ((DashboardPagination)this.modelMap.get(PAGINATION)).getSearch();
		String orderBy = ((DashboardPagination)this.modelMap.get(PAGINATION)).getOrderBy();
		
		int nbComputer;
		List<ComputerDto> computerList;
		// Apply changes
		if (search.equals(" ")) {
			computerList = this.computerService.list(page, size, orderBy).stream().map(this.computerMapper::modelToDto).collect(Collectors.toList());
			nbComputer = this.computerService.count();
		} else {
			computerList = this.computerService.listByName(search, page, size, orderBy).stream().map(this.computerMapper::modelToDto).collect(Collectors.toList());
			nbComputer = this.computerService.countByName(search);
		}
		((DashboardComputerList)this.modelMap.get(COMPUTER_LIST)).setList(computerList);
		((DashboardComputerList)this.modelMap.get(COMPUTER_LIST)).setNbComputer(nbComputer);
		
		int maxPage = nbComputer / size + ((nbComputer % size == 0) ? 0 : 1);
		((DashboardPagination)this.modelMap.get(PAGINATION)).setMaxPage(maxPage);
		
		int medPage;
		if (page < 3)
			medPage = 3;
		else
			medPage = (page < maxPage-2 || maxPage <= 4) ? page : maxPage-2;
		((DashboardPagination)this.modelMap.get(PAGINATION)).setMedianPage(medPage);
	}
}
