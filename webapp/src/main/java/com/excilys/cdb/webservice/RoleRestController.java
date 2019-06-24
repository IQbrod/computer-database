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

import com.excilys.cdb.dto.RoleDto;
import com.excilys.cdb.mapper.RoleDtoMapper;
import com.excilys.cdb.service.RoleService;
import com.excilys.cdb.validator.Validator;

@RestController
@RequestMapping(path="/api/roles", produces = "application/json")
@CrossOrigin(origins = "http://localhost:9000")
public class RoleRestController {
	private final RoleService roleService;
	private final RoleDtoMapper roleDtoMapper;
	private final Validator validator;
	
	public RoleRestController(RoleService roleService, RoleDtoMapper roleDtoMapper, Validator validator) {
		this.roleService = roleService;
		this.roleDtoMapper = roleDtoMapper;
		this.validator = validator;
	}
	
	@GetMapping
	public Iterable<RoleDto> getPart(
		@RequestParam(value="page") Integer page,
    	@RequestParam(value="size") Integer size
	) {
		return this.roleService.list(page, size).stream().map(this.roleDtoMapper::modelToDto).collect(Collectors.toList());
	}
	
	@PostMapping
	public void postRole(@RequestBody RoleDto role) {
		this.validator.validateRoleDto(role);
		this.roleService.create(this.roleDtoMapper.dtoToModel(role));
	}
	
	@PutMapping
	public void putRole(@RequestBody RoleDto role) {
		this.validator.validateRoleDto(role);
		this.roleService.update(this.roleDtoMapper.dtoToModel(role));
	}
	
	@DeleteMapping("/{id}")
	public void deleteRole(@PathVariable(value="id") Long id) {
		if (id > 2) {
			this.roleService.delete(this.roleDtoMapper.dtoToModel(new RoleDto(id)));
		} else {
			throw new RuntimeException();
		}
	}
	
	@GetMapping("/count")
	public long countComputers(@RequestParam(value="search", required = false) String search) {
		if (search == null) {
			return this.roleService.count();
		} else {
			return this.roleService.countByName(search);
		}
	}
}
