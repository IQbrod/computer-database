package com.excilys.cdb.service;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.spring.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class)
public class CompanyServiceTest {
	
	@Autowired
	private CompanyService serviceInstance;	
	
	@Test
	public void CreateTest() throws Exception {
		serviceInstance.create(new Company(80,"Entreprise"));
	}
	
	@Test
	public void ReadTest() throws Exception {
		serviceInstance.read(80);
	}
	
	@Test
	public void UpdateTest() throws Exception {
		serviceInstance.update(new Company(5,"Updated"));
	}
	
	@Test
	public void DeleteTest() throws Exception {
		serviceInstance.delete(new Company(80,"DeleteMe"));
	}
	
	@Test
	public void listAllTest() throws Exception {
		serviceInstance.listAllElements();
	}
	
	@Test
	public void listTest() throws Exception {
		serviceInstance.list(1, 20);
	}
	
	@Test
	public void countTest() throws Exception {
		assertEquals(serviceInstance.listAllElements().size(),serviceInstance.count());
	}
}
