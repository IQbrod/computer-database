package com.excilys.cdb.service;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.exception.DatabaseProblemException;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService extends AbstractService<Company>{
	
	public CompanyService(CompanyDao companyDao) throws DatabaseProblemException {
		super(companyDao);
	}
}
