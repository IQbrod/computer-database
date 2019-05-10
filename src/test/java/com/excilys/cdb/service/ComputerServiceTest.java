package com.excilys.cdb.service;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.Computer;

public class ComputerServiceTest {

	@Test
	public void instanceTest() throws DatabaseProblemException {
		ComputerService.getInstance();
	}
	
	@Test
	public void createTest() throws Exception {
		ComputerService.getInstance().create(new Computer(888,"Ordinateur",null,null,0));
	}
	
	@Test
	public void readTest() throws Exception {
		ComputerService.getInstance().read(8);
	}
	
	@Test
	public void updateTest() throws Exception {
		ComputerService.getInstance().update(new Computer(27,"Updated",null,null,0));
	}
	
	@Test
	public void deleteTest() throws Exception {
		ComputerService.getInstance().delete(new Computer(888,"DeleteMe",null,null,0));
	}
	
	@Test
	public void listAllTest() throws Exception {
		ComputerService.getInstance().listAllElements();
	}
	
	@Test
	public void listTest() throws Exception {
		ComputerService.getInstance().list(1, 20);
	}
	
	@Test
	public void countTest() throws Exception {
		assertEquals(ComputerService.getInstance().listAllElements().size(),ComputerService.getInstance().count());
	}
}
