package com.excilys.cdb.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.RoleDto;
import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.RoleService;

@Component
public class UserDtoMapper extends DtoMapper<UserDto, User>{	
	private final RoleService roleService;
	private final RoleDtoMapper roleDtoMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserDtoMapper(RoleService roleService, RoleDtoMapper roleDtoMapper, PasswordEncoder passwordEncoder) {
		this.roleService = roleService;
		this.roleDtoMapper = roleDtoMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User dtoToModel(UserDto dtoObject) {
		return new User(
			dtoObject.getId(),
			dtoObject.getUsername(),
			this.passwordEncoder.encode(dtoObject.getPassword()),
			dtoObject.getRoleId()
		);
	}

	@Override
	public UserDto modelToDto(User modelObject) {
		RoleDto roleDto = this.roleDtoMapper.modelToDto(this.roleService.read(modelObject.getRoleId()));
		
		return new UserDto(
			modelObject.getId(),
			modelObject.getUsername(),
			modelObject.getPassword(),
			roleDto.getId(),
			roleDto.getName()
		);
	}
	
}
