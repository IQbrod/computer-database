package com.excilys.cdb.servlet;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
public class AddComputerServlet {
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private Validator validator;
	
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
		@RequestParam("companyId") String company_id
	) {
		ComputerDto computerDto = new ComputerDto("0", name, introduced.equals("") ? null : introduced, discontinued.equals("") ? null : discontinued, company_id,"None");
		this.validator.validateComputerDto(computerDto);
		this.computerService.create(this.computerMapper.dtoToModel(computerDto));
		return new RedirectView("dashboard");
	}
}
