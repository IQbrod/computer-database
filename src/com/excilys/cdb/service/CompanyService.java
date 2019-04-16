package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Company;

public class CompanyService extends Service<CompanyDto, Company>{
	
	public CompanyService(Dao<Company> d) {
		super(new CompanyMapper(), d);
	}

	@Override
	public List<CompanyDto> listAllElements() {
		// TODO Auto-generated method stub
		return null;
	}
}
