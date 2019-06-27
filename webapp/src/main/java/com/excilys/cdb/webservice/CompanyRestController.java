package com.excilys.cdb.webservice;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.mapper.CompanyDtoMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.validator.Validator;

@RestController
@RequestMapping(path="/api/companies", produces = "application/json")
@CrossOrigin(origins = "http://localhost:9000")
public class CompanyRestController {
	private final CompanyService companyService;
	private final CompanyDtoMapper companyDtoMapper;
	private final Validator validator;
	
	public CompanyRestController(CompanyService companyService, CompanyDtoMapper companyDtoMapper, Validator validator) {
		this.companyService = companyService;
		this.companyDtoMapper = companyDtoMapper;
		this.validator = validator;
	}
	
	@GetMapping
	public Iterable<CompanyDto> getPart(
		@RequestParam(value="page", required = false) Integer page,
    	@RequestParam(value="size", required = false) Integer size,
    	@RequestParam(value="search", required = false) String search,
    	@RequestParam(value="orderBy", required = false) String orderBy
	) {
		if (page == null && size == null && search == null && orderBy == null) {
			return this.companyService.listAllElements().stream().map(this.companyDtoMapper::modelToDto).collect(Collectors.toList());
		} else if (page == null || size == null || search == null || orderBy == null) {
			return null;
		} else {
			return this.companyService.list(page, size, search, orderBy).stream().map(this.companyDtoMapper::modelToDto).collect(Collectors.toList());
		}
	}
	
	@PostMapping
	public void postCompany(@RequestBody CompanyDto company) {
		this.validator.validateCompanyDto(company);
		this.companyService.create(this.companyDtoMapper.dtoToModel(company));
	}
	
	@PutMapping
	public void putCompany(@RequestBody CompanyDto company) {
		this.validator.validateCompanyDto(company);
		this.companyService.update(this.companyDtoMapper.dtoToModel(company));
	}
	
	@DeleteMapping("/{id}")
	public void deleteComputer(@PathVariable(value="id") Long id) {
		this.companyService.delete(this.companyDtoMapper.dtoToModel(new CompanyDto(id)));
	}
	
	@GetMapping("/count")
	public long countCompanies(@RequestParam(value="search", required = false) String search) {
		if (search == null) {
			return this.companyService.count();
		} else {
			return this.companyService.countByName(search);
		}
	}
}
