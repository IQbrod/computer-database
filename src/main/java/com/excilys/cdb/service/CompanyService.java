package com.excilys.cdb.service;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Company;

public class CompanyService extends Service<CompanyDto, Company>{
	private static CompanyService instance = new CompanyService(); 
	
	private CompanyService() {
		super(CompanyMapper.getInstance(), CompanyDao.getInstance());
	}
	
	public static CompanyService getInstance() {
		return instance;
	}
}
