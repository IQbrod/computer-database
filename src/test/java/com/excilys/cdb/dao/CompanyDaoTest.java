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
	public void TestRead() throws Exception {
		CompanyDao.getInstance().read(7);
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestReadMissingId() throws Exception {
		CompanyDao.getInstance().read(120);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestReadInvalidId() throws Exception {
		CompanyDao.getInstance().read(-8);
	}
	
	@Test
	public void TestCreate() throws Exception {
		CompanyDao.getInstance().create(new Company(75,"Entreprise"));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestCreateInvalidId() throws Exception {
		CompanyDao.getInstance().create(new Company(-7,"Invalide"));
	}
	
	@Test (expected = PrimaryKeyViolationException.class)
	public void TestCreateDuplicateId() throws Exception {
		CompanyDao.getInstance().create(new Company(4,"PrimaryKey"));
	}
}
