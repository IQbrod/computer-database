package com.excilys.cdb.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.ShouldBeSentToClientException;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.*;
import com.excilys.cdb.controller.model.ServletErrorModel;
import com.excilys.cdb.validator.Validator;

@Controller
public class EditionServlet {
	private static final String NEWCOMPUTER_PATTERN = "addComputer";
	private static final String EDITCOMPUTER_PATTERN = "editComputer";
	
	private final ComputerService computerService;
	private final CompanyService companyService;
	private final CompanyDtoMapper companyMapper;
	private final ComputerDtoMapper computerMapper;
	private final Validator validator;
	
	public EditionServlet(ComputerService computerService, CompanyService companyService, CompanyDtoMapper companyMapper, ComputerDtoMapper computerMapper, Validator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}
	
	@ModelAttribute("computer")
	public ComputerDto computerAttribute() {
		return new ComputerDto(0L);
	}
	
	@InitBinder
	public void allowEmptyDates(WebDataBinder binder) {  
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));  
	}
	
	@GetMapping(DashboardServlet.COMPUTER_PATTERN+"/{id}")
	public String getComputerById(
		@PathVariable("id") int id,	
		Model model
	) {
		model.addAttribute("computer", this.computerMapper.modelToDto(this.computerService.read(id)));
		model.addAttribute("companyList",this.companyService.listAllElements().stream().map(this.companyMapper::modelToDto).collect(Collectors.toList()));
		return EDITCOMPUTER_PATTERN;
	}
	
	@PutMapping(DashboardServlet.COMPUTER_PATTERN)
	public RedirectView updateComputer(
		@ModelAttribute("computer") ComputerDto computer,
		RedirectAttributes redir
	) {
		try {
			this.validator.validateComputerDto(computer);
			this.computerService.update(this.computerMapper.dtoToModel(computer));
		} catch (ShouldBeSentToClientException e) {
			redir.addFlashAttribute(ErrorServlet.ERROR_PATTERN, new ServletErrorModel(400, e.getMessage(), e.getClass().toString()));
			return new RedirectView(ErrorServlet.ERROR_PATTERN);
		}
		return new RedirectView(DashboardServlet.COMPUTER_PATTERN);
	}
	
	@GetMapping(DashboardServlet.COMPUTER_PATTERN+"/new")
	public String getNewComputer(Model model) {
		model.addAttribute("companyList",this.companyService.listAllElements().stream().map(this.companyMapper::modelToDto).collect(Collectors.toList()));
		return NEWCOMPUTER_PATTERN;
	}
	
	@PostMapping(DashboardServlet.COMPUTER_PATTERN)
	public RedirectView newComputer(
		@Valid @ModelAttribute("computer") ComputerDto computer
	) {
		this.validator.validateComputerDto(computer);
		this.computerService.create(this.computerMapper.dtoToModel(computer));
		return new RedirectView(DashboardServlet.COMPUTER_PATTERN);
	}
}
