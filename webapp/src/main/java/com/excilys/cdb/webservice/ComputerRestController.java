package com.excilys.cdb.webservice;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.ComputerDtoMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.Validator;

@RestController
@RequestMapping(path="/api/computers", produces = "application/json")
@CrossOrigin(origins = "http://localhost:9000")
public class ComputerRestController {
	private final ComputerService computerService;
	private final ComputerDtoMapper computerDtoMapper;
	private final Validator validator;
	
	public ComputerRestController(ComputerService computerService, ComputerDtoMapper computerDtoMapper, Validator validator) {
		this.computerService = computerService;
		this.computerDtoMapper = computerDtoMapper;
		this.validator = validator;
	}
	
	@GetMapping
	public Iterable<ComputerDto> getPart(
		@RequestParam(value="page") Integer page,
    	@RequestParam(value="size") Integer size,
    	@RequestParam(value="search") String search,
    	@RequestParam(value="orderBy") String orderBy
	) {
		return this.computerService.listByName(search, page, size, orderBy).stream().map(this.computerDtoMapper::modelToDto).collect(Collectors.toList());
	}
	
	@PostMapping
	public void postCompany(@RequestBody ComputerDto computer) {
		this.validator.validateComputerDto(computer);
		this.computerService.create(this.computerDtoMapper.dtoToModel(computer));
	}
	
	@PutMapping
	public void putComputer(@RequestBody ComputerDto computer) {
		this.validator.validateComputerDto(computer);
		this.computerService.update(this.computerDtoMapper.dtoToModel(computer));
	}
	
	@DeleteMapping("/{id}")
	public void deleteComputer(@PathVariable(value="id") Long id) {
		this.computerService.delete(this.computerDtoMapper.dtoToModel(new ComputerDto(id)));
	}
	
	@GetMapping("/count")
	public long countComputers(@RequestParam(value="search", required = false) String search) {
		if (search == null) {
			return this.computerService.count();
		} else {
			return this.computerService.countByName(search);
		}
	}
}
