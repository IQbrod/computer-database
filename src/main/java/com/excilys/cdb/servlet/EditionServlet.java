package com.excilys.cdb.servlet;

import java.util.stream.Collectors;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.*;
import com.excilys.cdb.validator.Validator;

@Controller
public class EditionServlet {
	private final ComputerService computerService;
	private final CompanyService companyService;
	private final CompanyMapper companyMapper;
	private final ComputerMapper computerMapper;
	private final Validator validator;
	
	public EditionServlet(ComputerService computerService, CompanyService companyService, CompanyMapper companyMapper, ComputerMapper computerMapper, Validator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
		this.validator = validator;
	}
	
	@GetMapping("/editComputer")
	public String get(
		@RequestParam("id") int id,	
		Model model
	) {
		model.addAttribute("computer", this.computerMapper.modelToDto(this.computerService.read(id)));
		model.addAttribute("companyList",this.companyService.listAllElements().stream().map(this.companyMapper::modelToDto).collect(Collectors.toList()));
		return "editComputer";
	}
	
	@PostMapping("/editComputer")
	public RedirectView post(
		@RequestParam("id") Integer id,
		@RequestParam("computerName") String name,
		@RequestParam("introduced") String introduced,
		@RequestParam("discontinued") String discontinued,
		@RequestParam("companyId") Integer company_id
	) {
		ComputerDto computerDto = new ComputerDto(id, name, introduced.equals("") ? null : introduced, discontinued.equals("") ? null : discontinued, company_id,"None");
		this.validator.validateComputerDto(computerDto);
		this.computerService.update(this.computerMapper.dtoToModel(computerDto));
		return new RedirectView("dashboard");
	}
	
	@GetMapping("/addComputer")
	public String get(Model model) {
		model.addAttribute("companyList",this.companyService.listAllElements().stream().map(this.companyMapper::modelToDto).collect(Collectors.toList()));
		return "addComputer";
	}
	
	@PostMapping("/addComputer")
	public RedirectView post(
		@RequestParam("computerName") String name,
		@RequestParam("introduced") String introduced,
		@RequestParam("discontinued") String discontinued,
		@RequestParam("companyId") Integer company_id
	) {
		ComputerDto computerDto = new ComputerDto(0, name, introduced.equals("") ? null : introduced, discontinued.equals("") ? null : discontinued, company_id,"None");
		this.validator.validateComputerDto(computerDto);
		this.computerService.create(this.computerMapper.dtoToModel(computerDto));
		return new RedirectView("dashboard");
	}
}
