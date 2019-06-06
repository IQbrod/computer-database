package com.excilys.cdb.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.User;
import com.excilys.cdb.service.RoleService;

@Component
public class UserMapper {
	private final RoleService roleService;
	private final RoleMapper roleMapper;

	public UserMapper(RoleService roleService, RoleMapper roleMapper) {
		this.roleService = roleService;
		this.roleMapper = roleMapper;
	}

	public UserDetails map(User user) {
		return new UserDetails() {
			private static final long serialVersionUID = 6062019L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
				list.add(roleMapper.map(roleService.read(user.getRoleId())));
				return list;
			}

			@Override
			public String getPassword() {
				return user.getPassword();
			}

			@Override
			public String getUsername() {
				return user.getUsername();
			}

			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isEnabled() {
				return true;
			}
		};
	}
}
