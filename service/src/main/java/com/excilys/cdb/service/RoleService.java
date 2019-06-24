package com.excilys.cdb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.repository.RoleDao;
import com.excilys.cdb.repository.UserDao;
import com.excilys.cdb.model.Role;

@Service
public class RoleService extends AbstractService<Role>{
	private final UserDao userDao;
	
	public RoleService(RoleDao roleDao, UserDao userDao) {
		super(roleDao);
		this.userDao = userDao;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Role modelObject) {
		this.userDao.updateByRoleId(modelObject.getId());
		this.dao.delete(modelObject);
	}
}
