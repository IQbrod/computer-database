package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.ShouldBeSentToClientException;
import com.excilys.cdb.exception.ShouldOnlyBeLoggedException;
import com.excilys.cdb.exception.UnexpectedServletException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.model.dashboard.DashboardComputerList;
import com.excilys.cdb.servlet.model.dashboard.DashboardPagination;
import com.excilys.cdb.spring.AppConfig;

public class DashboardServlet extends Servlet {	
	private static final long serialVersionUID = 3052019L;

	private final ComputerService computerService;
	private final ComputerMapper computerMapper;
	
	public DashboardServlet() {		
		try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
			
			this.modelMap.put("pagination", context.getBean(DashboardPagination.class));
			this.modelMap.put("computerList", context.getBean(DashboardComputerList.class));
			
			this.computerService = context.getBean(ComputerService.class);
			this.computerMapper = context.getBean(ComputerMapper.class);

		}
	}
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {	
		try {
			
			this.setupDashboard(request);
			this.flushSetup(request);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward( request, response );
			
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
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		String listId[] = request.getParameter("selection").split(",");
		
		try {
			
			for (String id : listId) {
				this.computerService.delete(this.computerMapper.dtoToModel(new ComputerDto(id)));
			}
			response.sendRedirect(this.getServletContext().getContextPath()+"/?page="+ ((DashboardPagination)this.modelMap.get("pagination")).getPage() +"&size="+ ((DashboardPagination)this.modelMap.get("pagination")).getSize());
			
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

	protected void setupDashboard(HttpServletRequest request) {
		// Setup from request
		if (request.getParameter("page") == null && request.getParameter("size") == null && request.getParameter("search") == null) {
			((DashboardPagination)this.modelMap.get("pagination")).setDefault();
		}
			
		if (request.getParameter("page") != null)
			((DashboardPagination)this.modelMap.get("pagination")).setPage(Integer.valueOf(request.getParameter("page")));
		if (request.getParameter("size") != null)
			((DashboardPagination)this.modelMap.get("pagination")).setSize(Integer.valueOf(request.getParameter("size")));
		if (request.getParameter("search") != null)
			((DashboardPagination)this.modelMap.get("pagination")).setSearch(request.getParameter("search"));
		if (request.getParameter("orderBy") != null)
			((DashboardPagination)this.modelMap.get("pagination")).setOrderBy(request.getParameter("orderBy"));
		
		
		int page = ((DashboardPagination)this.modelMap.get("pagination")).getPage();
		int size = ((DashboardPagination)this.modelMap.get("pagination")).getSize();
		String search = ((DashboardPagination)this.modelMap.get("pagination")).getSearch();
		String orderBy = ((DashboardPagination)this.modelMap.get("pagination")).getOrderBy();
		
		int nbComputer;
		List<ComputerDto> computerList;
		// Apply changes
		if (search.equals(" ")) {
			computerList = this.computerService.list(page, size, orderBy).stream().map(dto -> this.computerMapper.modelToDto(dto)).collect(Collectors.toList());
			nbComputer = this.computerService.count();
		} else {
			computerList = this.computerService.listByName(search, page, size, orderBy).stream().map(dto -> this.computerMapper.modelToDto(dto)).collect(Collectors.toList());
			nbComputer = this.computerService.countByName(search);
		}
		((DashboardComputerList)this.modelMap.get("computerList")).setList(computerList);
		((DashboardComputerList)this.modelMap.get("computerList")).setNbComputer(nbComputer);
		
		int maxPage = nbComputer / size + ((nbComputer % size == 0) ? 0 : 1);
		((DashboardPagination)this.modelMap.get("pagination")).setMaxPage(maxPage);
		
		int medPage = (page < 3) ? 3 : (page < maxPage-2 || maxPage <= 4) ? page : maxPage-2;
		((DashboardPagination)this.modelMap.get("pagination")).setMedianPage(medPage);
	}
}
