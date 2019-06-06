package com.excilys.cdb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.controller.model.Pagination;

@Controller
@SessionAttributes( value=DashboardServlet.PAGINATION_PATTERN, types= {Pagination.class})
public class DashboardServlet {
	static final String PAGINATION_PATTERN = "pagination";
	protected static final String COMPUTER_PATTERN = "computers";
	private static final String DASHBOARD_PATTERN = "dashboard";
	
	private final ComputerService computerService;
	private final ComputerDtoMapper computerMapper;
	
	public DashboardServlet(ComputerService computerService, ComputerDtoMapper computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}
	
	@ModelAttribute(PAGINATION_PATTERN)
    public Pagination addPaginationToSessionScope() {
        return new Pagination();
    }
	
	@GetMapping(value="/")
	public RedirectView reset(SessionStatus status) {
		status.setComplete();
		return new RedirectView(COMPUTER_PATTERN);
	}
	
	@GetMapping(COMPUTER_PATTERN)
    public String getComputers(
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="size", required=false) Integer size,
    		@RequestParam(value="search", required=false) String search,
    		@RequestParam(value="orderBy", required=false) String orderBy,
    		@ModelAttribute(PAGINATION_PATTERN) Pagination pagination,
    		Model model
    ) {		
		if (page != null)
			pagination.setPage(page);
		if (size != null)
			pagination.setSize(size);
		if (search != null)
			pagination.setSearch(search);
		if (orderBy != null)
			pagination.setOrderBy(orderBy);

		List<ComputerDto> computerList = this.computerService.listByName(pagination.getSearch(), pagination.getPage(), pagination.getSize(), pagination.getOrderBy()).stream().map(this.computerMapper::modelToDto).collect(Collectors.toList());
		pagination.setNbComputer(this.computerService.countByName(pagination.getSearch()));
		model.addAttribute("computerList", computerList);
		long maxPage = pagination.getNbComputer() / pagination.getSize() + ((pagination.getNbComputer() % pagination.getSize() == 0) ? 0 : 1);
		pagination.setMaxPage(maxPage);
		
		if (pagination.getPage() < 3)
			pagination.setMedianPage(3);
		else
			pagination.setMedianPage((pagination.getPage() < maxPage-2 || maxPage <= 4) ? pagination.getPage() : maxPage-2);
		
        return DASHBOARD_PATTERN;
    }
	
	@DeleteMapping(COMPUTER_PATTERN)
	public RedirectView deleteComputers(
		@RequestParam(value="selection") Long[] selection,
		@ModelAttribute(PAGINATION_PATTERN) Pagination pagination
	) {
		if ((selection.length == pagination.getSize() && pagination.getPage() > 1) || pagination.getNbComputer() - (pagination.getPage()-1) * pagination.getSize() == selection.length)
			pagination.setPage(pagination.getPage()-1);
		
		for (Long id : selection) {
			this.computerService.delete(this.computerMapper.dtoToModel(new ComputerDto(id)));
		}
		return new RedirectView(COMPUTER_PATTERN);
	}
}