package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.*;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Computer;

public class ComputerDaoTest {
	
	@Test
	public void TestDbConnection() throws DatabaseProblemException {
		ComputerDao.getInstance();
	}
	
	/*-- READ --*/
	@Test
	public void TestRead() throws Exception {
		ComputerDao.getInstance().read(7);
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestReadMissingId() throws Exception {
		ComputerDao.getInstance().read(870);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestReadInvalidId() throws Exception {
		ComputerDao.getInstance().read(-8);
	}
	
	/*-- CREATE --*/
	// id
	@Test
	public void TestCreateMinimal() throws Exception {
		ComputerDao.getInstance().create(new Computer(750,"Ordinateur",null,null,0));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestCreateInvalidId() throws Exception {
		ComputerDao.getInstance().create(new Computer(-7,"Ordinateur",null,null,0));
	}
	
	@Test (expected = PrimaryKeyViolationException.class)
	public void TestCreateDuplicateId() throws Exception {
		ComputerDao.getInstance().create(new Computer(4,"Ordinateur",null,null,0));
	}
	
	// dateIntro
	@Test
	public void TestCreateDateIntroduction() throws Exception {
		ComputerDao.getInstance().create(new Computer(753,"Ordinateur",Timestamp.valueOf("2017-05-21 17:23:17"),null,0));
	}
	
	// dateDisc
	@Test
	public void TestCreateDateDiscontinued() throws Exception {
		ComputerDao.getInstance().create(new Computer(754,"Ordinateur",null,Timestamp.valueOf("2017-05-21 17:23:17"),0));
	}
	
	// dateIntro + dateDisc
	@Test
	public void TestCreateMixedDates() throws Exception {
		ComputerDao.getInstance().create(new Computer(755,"Ordinateur",Timestamp.valueOf("2017-05-21 17:23:17"),Timestamp.valueOf("2019-05-21 17:23:17"),0));
	}
	
	@Test (expected = InvalidDateOrderException.class)
	public void TestCreateInvalidMixedDates() throws Exception {
		ComputerDao.getInstance().create(new Computer(752,"Ordinateur",Timestamp.valueOf("2017-05-21 17:23:17"),Timestamp.valueOf("2015-05-21 17:23:17"),0));
	}
	
	// Company id
	@Test
	public void TestCreate() throws Exception {
		ComputerDao.getInstance().create(new Computer(751,"Ordinateur",null,null,2));
	}
	
	@Test (expected = ForeignKeyViolationException.class)
	public void TestCreateInvalidCompanyId() throws Exception {
		ComputerDao.getInstance().create(new Computer(752,"Ordinateur",null,null,-5));
	}
	
	@Test (expected = ForeignKeyViolationException.class)
	public void TestCreateTooHighCompanyId() throws Exception {
		ComputerDao.getInstance().create(new Computer(752,"Ordinateur",null,null,750));
	}
	
	/*-- UPDATE --*/
	@Test
	public void TestUpdate() throws Exception {
		ComputerDao.getInstance().update(new Computer(8,"Computer",Timestamp.valueOf("2017-05-21 17:23:17"),Timestamp.valueOf("2019-05-21 17:23:17"),5));
	}
	
	@Test
	public void TestUpdateReset() throws Exception {
		ComputerDao.getInstance().update(new Computer(8,"Ordinateur",null,null,0));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestUpdateUnexistingComputer() throws Exception {
		ComputerDao.getInstance().update(new Computer(870,"Ordinateur",null,null,0));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestUpdateInvalidComputer() throws Exception {
		ComputerDao.getInstance().update(new Computer(-7,"Ordinateur",null,null,0));
	}
	
	/*-- DELETE --*/
	@Test
	public void TestDelete() throws Exception {
		ComputerDao.getInstance().delete(new Computer(750,"Suppression",null,null,0));
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteUnexistingCompany() throws Exception {
		ComputerDao.getInstance().delete(new Computer(870,"Ordinateur",null,null,0));
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteInvalidCompany() throws Exception {
		ComputerDao.getInstance().delete(new Computer(-7,"Ordinateur",null,null,0));
	}
	
	@Test
	public void TestDeleteById() throws Exception {
		ComputerDao.getInstance().deleteById(751);
		ComputerDao.getInstance().deleteById(753);
		ComputerDao.getInstance().deleteById(754);
		ComputerDao.getInstance().deleteById(755);
	}
	
	@Test (expected = FailedSQLQueryException.class)
	public void TestDeleteByIdUnexistingCompany() throws Exception {
		ComputerDao.getInstance().deleteById(850);
	}
	
	@Test (expected = InvalidIdException.class)
	public void TestDeleteByIdInvalidCompany() throws Exception {
		ComputerDao.getInstance().deleteById(-7);
	}
	
	/*-- LISTALL --*/
	@Test
	public void TestListAll() throws Exception {
		ComputerDao.getInstance().listAll();
	}
	
	/*-- LIST --*/
	@Test
	public void TestList() throws Exception {
		assertEquals(20,ComputerDao.getInstance().list(1,20).size());
	}
	
	@Test
	public void TestListTooFar() throws Exception {
		assertEquals(0,ComputerDao.getInstance().list(40,100).size());
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void TestListInvalidPageZero() throws Exception {
		ComputerDao.getInstance().list(0, 20);
	}
	
	@Test (expected = InvalidPageValueException.class)
	public void TestListInvalidPageNegative() throws Exception {
		ComputerDao.getInstance().list(-9, 20);
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void TestListInvalidSize() throws Exception {
		ComputerDao.getInstance().list(1, -5);
	}
	
	@Test (expected = InvalidPageSizeException.class)
	public void TestListEmptySize() throws Exception {
		ComputerDao.getInstance().list(5,0).size();
	}
}
