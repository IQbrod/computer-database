package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.spring.WebMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= WebMvcConfig.class)
public class CompanyDaoTest {
	
	@Autowired
	private CompanyDao daoInstance;
	
	
	/*-- READ --*/
	@Test
	public void TestRead() throws Exception {
		assertEquals(7,daoInstance.read(7).getId());
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestReadMissingId() throws Exception {
		daoInstance.read(120);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestReadInvalidId() throws Exception {
		daoInstance.read(-8);
	}
	
	/*-- CREATE --*/
	@Test
	public void TestCreate() throws Exception {
		assertEquals(75,daoInstance.create(new Company(75,"Entreprise")).getId());
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestCreateInvalidId() throws Exception {
		daoInstance.create(new Company(-7,"Invalide"));
	}
	
	@Test (expected = KeyViolationException.class)
	public void TestCreateDuplicateId() throws Exception {
		daoInstance.create(new Company(4,"PrimaryKey"));
	}
	
	/*-- UPDATE --*/
	@Test
	public void TestUpdate() throws Exception {
		assertEquals("Company",daoInstance.update(new Company(8,"Company")).getName());
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestUpdateUnexistingCompany() throws Exception {
		daoInstance.update(new Company(850,"Inexistant"));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestUpdateInvalidCompany() throws Exception {
		daoInstance.update(new Company(-7,"Impossible"));
	}
	
	/*-- DELETE --*/
	@Test
	public void TestDelete() throws Exception {
		assertNotNull(daoInstance.delete(new Company(75,"Valide")));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteUnexistingCompany() throws Exception {
		daoInstance.delete(new Company(850,"Inexistant"));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteInvalidCompany() throws Exception {
		daoInstance.delete(new Company(-7,"Impossible"));
	}
	
	@Test
	public void TestDeleteById() throws Exception {
		daoInstance.create(new Company(75,"Entreprise"));
		assertNotNull(daoInstance.deleteById(75));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteByIdUnexistingCompany() throws Exception {
		daoInstance.deleteById(850);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteByIdInvalidCompany() throws Exception {
		daoInstance.deleteById(-7);
	}
	
	/*-- LISTALL --*/
	@Test
	public void TestListAll() throws Exception {
		assertTrue(daoInstance.listAll().size() > 0);
	}
	
	/*-- LIST --*/
	@Test
	public void TestList() throws Exception {
		assertEquals(20,daoInstance.list(1,20).size());
	}
	
	@Test
	public void TestListTooFar() throws Exception {
		assertEquals(0,daoInstance.list(5,100).size());
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void TestListInvalidPageZero() throws Exception {
		daoInstance.list(0, 20);
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void TestListInvalidPageNegative() throws Exception {
		daoInstance.list(-9, 20);
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void TestListInvalidSize() throws Exception {
		daoInstance.list(1, -5);
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void TestListEmptySize() throws Exception {
		daoInstance.list(5,0).size();
	}
}
