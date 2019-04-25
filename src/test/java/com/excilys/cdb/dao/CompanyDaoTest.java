package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import org.junit.*;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Company;

public class CompanyDaoTest {
	
	@Test
	public void TestDbConnection() throws DatabaseProblemException {
		CompanyDao.getInstance();
	}
	
	/*-- READ --*/
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
	
	/*-- CREATE --*/
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
	
	/*-- UPDATE --*/
	@Test
	public void TestUpdate() throws Exception {
		CompanyDao.getInstance().update(new Company(8,"Company"));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestUpdateUnexistingCompany() throws Exception {
		CompanyDao.getInstance().update(new Company(850,"Inexistant"));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestUpdateInvalidCompany() throws Exception {
		CompanyDao.getInstance().update(new Company(-7,"Impossible"));
	}
	
	/*-- DELETE --*/
	@Test
	public void TestDelete() throws Exception {
		CompanyDao.getInstance().delete(new Company(75,"Valide"));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteUnexistingCompany() throws Exception {
		CompanyDao.getInstance().delete(new Company(850,"Inexistant"));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteInvalidCompany() throws Exception {
		CompanyDao.getInstance().delete(new Company(-7,"Impossible"));
	}
	
	@Test
	public void TestDeleteById() throws Exception {
		CompanyDao.getInstance().create(new Company(75,"Entreprise"));
		CompanyDao.getInstance().deleteById(75);
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteByIdUnexistingCompany() throws Exception {
		CompanyDao.getInstance().deleteById(850);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteByIdInvalidCompany() throws Exception {
		CompanyDao.getInstance().deleteById(-7);
	}
	
	/*-- LISTALL --*/
	@Test
	public void TestListAll() throws Exception {
		CompanyDao.getInstance().listAll();
	}
	
	/*-- LIST --*/
	@Test
	public void TestList() throws Exception {
		assertEquals(20,CompanyDao.getInstance().list(1,20).size());
	}
	
	@Test
	public void TestListTooFar() throws Exception {
		assertEquals(0,CompanyDao.getInstance().list(5,100).size());
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void TestListInvalidPageZero() throws Exception {
		CompanyDao.getInstance().list(0, 20);
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void TestListInvalidPageNegative() throws Exception {
		CompanyDao.getInstance().list(-9, 20);
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void TestListInvalidSize() throws Exception {
		CompanyDao.getInstance().list(1, -5);
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void TestListEmptySize() throws Exception {
		assertEquals(0,CompanyDao.getInstance().list(5,0).size());
	}
}
