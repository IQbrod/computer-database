package com.excilys.cdb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.model.Company;

@Service
@Transactional
public class CompanyService extends AbstractService<Company>{
	private final ComputerDao computerDao;
	
	public CompanyService(CompanyDao companyDao, ComputerDao computerDao) {
		super(companyDao);
		this.computerDao = computerDao;
	}
	
	@Override
	public Company delete(Company modelObject) {
		this.computerDao.deleteByCompanyId(modelObject.getId());
		return this.dao.delete(modelObject);
	}
}
