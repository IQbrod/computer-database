package com.excilys.cdb.service;

import org.springframework.stereotype.Service;

import com.excilys.cdb.repository.RoleDao;
import com.excilys.cdb.model.Role;

@Service
public class RoleService extends AbstractService<Role>{
	public RoleService(RoleDao roleDao) {
		super(roleDao);
	}
}
