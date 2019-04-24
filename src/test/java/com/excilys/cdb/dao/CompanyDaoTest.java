package com.excilys.cdb.dao;

import org.junit.*;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Company;

public class CompanyDaoTest {
	
	@Test
	public void TestDbConnection() throws DatabaseProblemException {
		CompanyDao.getInstance();
	}
	
	@Test
	public void TestCRUD() throws Exception {
		try {
			CompanyDao.getInstance().read(75);
		} catch (FailedSQLQueryException exception) {
			CompanyDao.getInstance().create(new Company(75,"Mon Entreprise"));
		}
	}
}
