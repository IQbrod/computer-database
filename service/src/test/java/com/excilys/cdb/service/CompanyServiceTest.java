package com.excilys.cdb.service;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.spring.TestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfiguration.class)
public class CompanyServiceTest {
	
	private CompanyService serviceInstance;
	@Autowired
	void setInstance(CompanyService beanInjection) {
		this.serviceInstance = beanInjection;
	}
	
	@Test
	public void CreateTest() throws Exception {
		serviceInstance.create(new Company(80,"Entreprise"));
	}
	
	@Test
	public void ReadTest() throws Exception {
		assertEquals(80,serviceInstance.read(80).getId());
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
		assertTrue(serviceInstance.listAllElements().size() > 0);
	}
	
	@Test
	public void listTest() throws Exception {
		assertEquals(20,serviceInstance.list(1, 20).size());
	}
	
	@Test
	public void countTest() throws Exception {
		assertEquals(serviceInstance.listAllElements().size(),serviceInstance.count());
	}
}
