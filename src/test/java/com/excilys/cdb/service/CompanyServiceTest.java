package com.excilys.cdb.service;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.exception.*;

public class CompanyServiceTest {

	@Test
	public void InstanceTest() throws DatabaseProblemException {
		CompanyService.getInstance();
	}
	
	@Test
	public void CreateTest() throws Exception {
		CompanyService.getInstance().create(new CompanyDto("80","Entreprise"));
	}
	
	@Test
	public void ReadTest() throws Exception {
		CompanyService.getInstance().read("80");
	}
	
	@Test
	public void UpdateTest() throws Exception {
		CompanyService.getInstance().update(new CompanyDto("5","Updated"));
	}
	
	@Test
	public void DeleteTest() throws Exception {
		CompanyService.getInstance().delete(new CompanyDto("80","DeleteMe"));
	}
	
	@Test
	public void listAllTest() throws Exception {
		CompanyService.getInstance().listAllElements();
	}
	
	@Test
	public void listTest() throws Exception {
		CompanyService.getInstance().list("1", "20");
	}
	
	@Test
	public void countTest() throws Exception {
		assertEquals(CompanyService.getInstance().listAllElements().size(),CompanyService.getInstance().count());
	}
}
