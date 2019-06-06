package com.excilys.cdb.mapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Role;

@Component
public class RoleMapper {
	public GrantedAuthority map(Role role) {
		return new SimpleGrantedAuthority("ROLE_"+role.getName());
	}
}
