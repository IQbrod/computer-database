package com.excilys.cdb.service;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.exception.DatabaseProblemException;
import com.excilys.cdb.model.Company;

public class CompanyService extends Service<Company>{
	private static CompanyService instance = null; 
	
	private CompanyService() throws DatabaseProblemException {
		super(CompanyDao.getInstance());
	}
	
	public static CompanyService getInstance() throws DatabaseProblemException {
		if (instance == null)
			instance = new CompanyService();
		return instance;
	}
}
