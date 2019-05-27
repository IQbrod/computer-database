package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.spring.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfiguration.class)
public class ComputerDaoTest {
	
	private ComputerDao daoInstance;
	
	@Autowired
	void setInstance(ComputerDao beanInjection) {
		this.daoInstance = beanInjection;
	}
	
	/*-- READ --*/
	@Test
	public void TestRead() throws Exception {
		assertEquals(7,daoInstance.read(7).getId());
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestReadMissingId() throws Exception {
		daoInstance.read(870);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestReadInvalidId() throws Exception {
		daoInstance.read(-8);
	}
	
	/*-- CREATE --*/
	// id
	@Test
	public void TestCreateDeleteMinimal() throws Exception {
		assertEquals(750,daoInstance.create(new Computer(750,"Ordinateur",null,null,0L)).getId());
		daoInstance.delete(new Computer(750,"Suppression",null,null,0L));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestCreateInvalidId() throws Exception {
		daoInstance.create(new Computer(-7,"Ordinateur",null,null,0L));
	}
	
	@Test (expected = KeyViolationException.class)
	public void TestCreateDuplicateId() throws Exception {
		daoInstance.create(new Computer(4,"Ordinateur",null,null,0L));
	}
	
	// dateIntro
	@Test
	public void TestCreateDateIntroductionAndDeleteById() throws Exception {
		assertEquals("2017-05-27 12:00:00.0",daoInstance.create(new Computer(753,"Ordinateur",Timestamp.valueOf("2017-05-27 12:00:00"),null,0L)).getIntroduced().toString());
		daoInstance.deleteById(753);
	}
	
	// dateDisc
	@Test
	public void TestCreateDateDiscontinued() throws Exception {
		assertEquals("2017-05-27 12:00:00.0",daoInstance.create(new Computer(754,"Ordinateur",null,Timestamp.valueOf("2017-05-27 12:00:00"),0L)).getDiscontinued().toString());
		daoInstance.deleteById(754);
	}
	
	// dateIntro + dateDisc
	@Test
	public void TestCreateMixedDates() throws Exception {
		assertEquals(755,daoInstance.create(new Computer(755,"Ordinateur",Timestamp.valueOf("2017-05-27 12:00:00"),Timestamp.valueOf("2017-05-27 12:50:00"),0L)).getId());
		daoInstance.deleteById(755);
	}
	
	// Company id
	@Test
	public void TestCreate() throws Exception {
		assertEquals(2,daoInstance.create(new Computer(751,"Ordinateur",null,null,0L)).getCompanyId());
		daoInstance.deleteById(751);
	}
	
	@Test (expected = KeyViolationException.class)
	public void TestCreateInvalidCompanyId() throws Exception {
		daoInstance.create(new Computer(752,"Ordinateur",null,null,0L));
	}
	
	@Test (expected = KeyViolationException.class)
	public void TestCreateTooHighCompanyId() throws Exception {
		daoInstance.create(new Computer(752,"Ordinateur",null,null,0L));
	}
	
	/*-- UPDATE --*/
	@Test
	public void TestUpdate() throws Exception {
		assertNotNull(daoInstance.update(new Computer(8,"Computer",Timestamp.valueOf("2017-05-27 12:00:00"),Timestamp.valueOf("2017-05-27 23:00:00"),0L)));
	}
	
	@Test
	public void TestUpdateReset() throws Exception {
		assertNotNull(daoInstance.update(new Computer(8,"Ordinateur",null,null,0L)));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestUpdateUnexistingComputer() throws Exception {
		daoInstance.update(new Computer(870,"Ordinateur",null,null,0L));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestUpdateInvalidComputer() throws Exception {
		daoInstance.update(new Computer(-7,"Ordinateur",null,null,0L));
	}
	
	/*-- DELETE --*/
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteUnexistingCompany() throws Exception {
		daoInstance.delete(new Computer(870,"Ordinateur",null,null,0L));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteInvalidCompany() throws Exception {
		daoInstance.delete(new Computer(-7,"Ordinateur",null,null,0L));
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
		assertEquals(0,daoInstance.list(40,100).size());
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
