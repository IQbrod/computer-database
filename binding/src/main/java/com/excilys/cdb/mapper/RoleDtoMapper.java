package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.RoleDto;
import com.excilys.cdb.model.Role;

@Component
public class RoleDtoMapper extends DtoMapper<RoleDto, Role>{	

	@Override
	public Role dtoToModel(RoleDto dtoObject) {
		return new Role(
			dtoObject.getId(),
			dtoObject.getName()
		);
	}

	@Override
	public RoleDto modelToDto(Role modelObject) {
		return new RoleDto(
			modelObject.getId(),
			modelObject.getName()
		);
	}
	
}
