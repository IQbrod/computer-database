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

import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.mapper.UserDtoMapper;
import com.excilys.cdb.service.UserService;
import com.excilys.cdb.validator.Validator;

@RestController
@RequestMapping(path="/api/users", produces = "application/json")
@CrossOrigin(origins = "http://localhost:9000")
public class UserRestController {
	private final UserService userService;
	private final UserDtoMapper userDtoMapper;
	private final Validator validator;
	
	public UserRestController(UserService userService, UserDtoMapper userDtoMapper, Validator validator) {
		this.userService = userService;
		this.userDtoMapper = userDtoMapper;
		this.validator = validator;
	}
	
	@GetMapping
	public Iterable<UserDto> getPart(
		@RequestParam(value="page") Integer page,
    	@RequestParam(value="size") Integer size,
    	@RequestParam(value="search") String search,
    	@RequestParam(value="orderBy") String orderBy
	) {
		return this.userService.list(page, size, search, orderBy).stream().map(this.userDtoMapper::modelToDto).collect(Collectors.toList());
	}
	
	@PostMapping
	public void postUser(@RequestBody UserDto user) {
		this.validator.validateUserDto(user);
		this.userService.create(this.userDtoMapper.dtoToModel(user));
	}
	
	@PutMapping
	public void putUser(@RequestBody UserDto user) {
		this.validator.validateUserDto(user);
		this.userService.update(this.userDtoMapper.dtoToModel(user));
	}
	
	@DeleteMapping("/{id}")
	public void deleteUsere(@PathVariable(value="id") Long id) {
		this.userService.delete(this.userDtoMapper.dtoToModel(new UserDto(id)));
	}
	
	@GetMapping("/count")
	public long countComputers(@RequestParam(value="search", required = false) String search) {
		if (search == null) {
			return this.userService.count();
		} else {
			return this.userService.countByName(search);
		}
	}
	
}
