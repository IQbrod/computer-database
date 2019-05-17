package com.excilys.cdb.servlet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.model.Pagination;

@Controller
@SessionAttributes( value=DashboardServlet.PAGINATION_PATTERN, types= {Pagination.class})
public class DashboardServlet {
	protected static final String PAGINATION_PATTERN = "pagination";
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerMapper computerMapper;
	
	@ModelAttribute(PAGINATION_PATTERN)
    public Pagination addPaginationToSessionScope() {
        return new Pagination();
    }
	
	@GetMapping(value={"/dashboard"})
    public String get(
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
		int nbComputer = this.computerService.countByName(pagination.getSearch());
		model.addAttribute("nbComputer", nbComputer);
		model.addAttribute("computerList", computerList);
		
		int maxPage = nbComputer / pagination.getSize() + ((nbComputer % pagination.getSize() == 0) ? 0 : 1);
		pagination.setMaxPage(maxPage);
		
		if (pagination.getPage() < 3)
			pagination.setMedianPage(3);
		else
			pagination.setMedianPage((pagination.getPage() < maxPage-2 || maxPage <= 4) ? pagination.getPage() : maxPage-2);
		
        return "dashboard";
    }
	
	@GetMapping(value="/")
	public RedirectView reset(@ModelAttribute(PAGINATION_PATTERN) Pagination pagination) {
		pagination.reset();
		return new RedirectView("dashboard");
	}
	
	@PostMapping(value= {"/","/dashboard"})
	public RedirectView post(
		@RequestParam(value="selection") Integer[] selection,
		@ModelAttribute(PAGINATION_PATTERN) Pagination pagination
	) {
		if (selection.length == pagination.getSize() && pagination.getPage() > 1)
			pagination.setPage(pagination.getPage()-1);
		
		for (Integer id : selection) {
			this.computerService.delete(this.computerMapper.dtoToModel(new ComputerDto(id.toString())));
		}
		return new RedirectView("dashboard");
	}
}